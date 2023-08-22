package com.bot.services;

import com.bot.database.CRUD;
import com.bot.entities.ScheduledTaskConfig;
import com.bot.entities.TaskJobConfig;
import com.bot.tasks.MoveCronTaskQuartz;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.novemberain.quartz.mongodb.MongoDBJobStore;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.IOException;

import static com.bot.application.ChestoBot.jobs;
import static com.bot.application.ChestoBot.scheduler;
import static com.bot.database.Connection.database;
import static com.bot.services.SelectTaskService.selected;
import static com.bot.utils.DocumentToObject.toObject;
import static com.bot.utils.ObjectToDocument.toDocument;

public class RunTaskService {
    // TODO: 13/06/2023 more tests should be done with ExecutorService, such as if a task will run repeatedly every day at specific time
    private static Guild guild;
    private static final long dayInSeconds= 8640;
    public static Guild getGuild() {
        return guild;
    }

    public void receiveCommand(@NotNull MessageReceivedEvent event) throws IOException {

        TextChannel textChannel = event.getChannel().asTextChannel();

        guild = event.getGuild();

        String[] inputTaskName = event.getMessage().getContentRaw().split(" ",2);
        if (!(inputTaskName.length > 1) || (inputTaskName[1].isEmpty())) {

            textChannel.sendMessage("No task name has set!").queue();

        } else {

            Document readDocument = CRUD.read(inputTaskName[1]);
            if (readDocument != null) {
                ScheduledTaskConfig runTask = (ScheduledTaskConfig) toObject(readDocument);
                if (selected) {

                    if (!runTask.getActive()) {

                        startSchedule(runTask);

                        //runTask.setActive(true);
                        CRUD.update(runTask, readDocument);
                        textChannel.sendMessage("Task " + runTask.getName() + " active!").queue();
                        selected = false;
                    } else {
                        textChannel.sendMessage("Task " + runTask.getName() + " is already active!").queue();
                    }

                    /*
                    if (!runTask.getActive()) {
                        runTask.setActive(true);
                        CRUD.update(runTask, readDocument);
                        textChannel.sendMessage("Task " + runTask.getName() + " active!").queue();
                        startSchedule(runTask);
                    }else {
                        textChannel.sendMessage("Task " + runTask.getName() + " is already active!").queue();
                    }
                    */
                } else {
                    textChannel.sendMessage("Must select a task first!").queue();
                }
            } else {
                textChannel.sendMessage("No such task name currently exist!").queue();
            }
        }
    }

    public static void startSchedule(ScheduledTaskConfig runTask) {

        SchedulerFactory shedFact = new StdSchedulerFactory();
        try {
            scheduler = shedFact.getScheduler();

            MongoDBJobStore jobStore = new MongoDBJobStore();

            JobKey jobKey = JobKey.jobKey("job" + runTask.getName(), "MoveCronTask");

            JobDataMap jobDataMap = new JobDataMap();
            jobDataMap.put("task", runTask);

            // Define the job and tie it to MoveCronTaskQuartz class
            JobDetail job = JobBuilder.newJob(MoveCronTaskQuartz.class)
                    .withIdentity(jobKey)
                    .usingJobData(jobDataMap)
                    .build();
            //job.getJobDataMap().put("PARAM_1_NAME", runTask);

            TriggerKey triggerKey = TriggerKey.triggerKey("trigger" + runTask.getName());

            // Trigger the job to run every 1 day at scheduled time
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withSchedule(CalendarIntervalScheduleBuilder
                            .calendarIntervalSchedule()
                            //.withIntervalInDays(1))
                            .withIntervalInSeconds(20))
                    .startAt(DateBuilder.todayAt(runTask.getTaskTime().getHour(),
                            runTask.getTaskTime().getMinute(),
                            runTask.getTaskTime().getSecond()))
                    .withIdentity(triggerKey)
                    .startNow()
                    .build();

            //scheduler.setJobFactory(new MyJobFactory());

            // Tell Quartz to schedule the job using our trigger
            scheduler.scheduleJob(job, trigger);

            // *Test purposes*
            TaskJobConfig quartz = new TaskJobConfig();
            quartz.setTaskName(runTask.getName());
            quartz.setJobKey(job.getKey());

            Document docJob = toDocument(quartz);

            database.getCollection("jobs").insertOne(docJob);
            // *Test purposes*

            // Mapping jobs for future manage (stop/resume)
            jobs.put(runTask.getName(), jobKey);


            // Start up the scheduler
            scheduler.start();
        } catch (SchedulerException | JsonProcessingException e) {
            e.printStackTrace();
        }

    }
}