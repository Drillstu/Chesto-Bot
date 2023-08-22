package com.bot.services;

import com.bot.database.CRUD;
import com.bot.entities.ScheduledTaskConfig;
import com.bot.entities.TaskJobConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;
import org.quartz.JobKey;
import org.quartz.SchedulerException;

import static com.bot.application.ChestoBot.scheduler;
import static com.bot.services.SelectTaskService.getSelectedTaskConfig;
import static com.bot.services.SelectTaskService.selected;

public class StopTaskService {

    public void receiveCommand(@NotNull MessageReceivedEvent event) throws JsonProcessingException, SchedulerException {

        TextChannel textChannel = event.getChannel().asTextChannel();

        String[] inputTaskName = event.getMessage().getContentRaw().split(" ",2);
        if (!(inputTaskName.length > 1) || (inputTaskName[1].isEmpty())) {

            textChannel.sendMessage("Must define task's name to stop!").queue();

        } else {

            Document readDocument = CRUD.read(inputTaskName[1],"jobs");
            if (readDocument != null) {

                // ScheduledTaskConfig stopTask = (ScheduledTaskConfig) toObject(readDocument);

                if (selected) {

                    ScheduledTaskConfig stopTask = getSelectedTaskConfig();
                    //if (stopTask.getActive()) {
                    stopSchedule(stopTask, readDocument);

                    stopTask.setActive(false);
                    CRUD.update(stopTask, readDocument);

                    textChannel.sendMessage("Task " + stopTask.getName() + " stopped!").queue();
                    selected = false;
                    //} else {
                    //    textChannel.sendMessage("Task " + stopTask.getName() + " is already stopped!").queue();
                    //}
                } else {
                    textChannel.sendMessage("Must select a task first!").queue();
                }
            } else {
                textChannel.sendMessage("No such task name currently exist!").queue();
            }
        }
    }

    public static void stopSchedule(ScheduledTaskConfig stopTask, Document stopDoc) throws SchedulerException, JsonProcessingException {

        //Scheduler stopScheduler = jobs.get(stopTask.getName());
        //stopScheduler.shutdown();
        //stopScheduler.interrupt()

        //JobKey stopJobKey = jobs.get(stopTask.getName());
        //scheduler.deleteJob(stopJobKey);


        // *Test purposes*
        /*
        SimpleModule module = new SimpleModule();
        module.addDeserializer(JobKey.class, new JobKeyDeserializer());
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(module);

         */

        String jsonDoc = stopDoc.toJson();
        ObjectMapper mapper = new ObjectMapper();
        TaskJobConfig key = mapper.readValue(jsonDoc, TaskJobConfig.class);
        //JobKey key = toJobKey(stopDoc);
        JobKey stopKey = new JobKey(key.getTaskName(), key.getJobKey().toString());
        scheduler.deleteJob(stopKey);
        // *Test purposes*


        //scheduler.pauseJob(stopJobKey);
        //scheduler.interrupt(stopJobKey);


    }
}
