package com.bot.services;

import com.bot.database.CRUD;
import com.bot.entities.ScheduledTaskConfig;
import com.bot.tasks.MoveCronTask;
import com.fasterxml.jackson.core.JsonProcessingException;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.bot.services.ExceptionService.mapExceptionMember;

public class RunMoveMemberService {
    // TODO: 13/06/2023 more tests should be done with ExecutorService, such as if a task will run repeatedly every day at specific time
    private static Guild guild;
    public static LocalDateTime dateTime;
    private static Future<?> futureTask;
    private static ScheduledExecutorService executorRef;
    private static final long dayInSeconds= 8640;
    public static ScheduledExecutorService getExecutorRef() { return executorRef; }
    public static Future<?> getFutureTask() { return futureTask; }
    public static Guild getGuild() {
        return guild;
    }

    public void receiveCommand(@NotNull MessageReceivedEvent event) throws JsonProcessingException {

        TextChannel textChannel = event.getChannel().asTextChannel();

        guild = event.getGuild();

        if ((VoiceService.getSourceID() == 0) || (VoiceService.getTargetID() == 0)) {
            textChannel.sendMessage("No source/target voice channel has set!").queue();
        } else if (MoveMemberTime.getTime() == null) {
            textChannel.sendMessage("No time has set!").queue();
        } else {

            String[] inputTaskName = event.getMessage().getContentRaw().split(" ",2);
            if (!(inputTaskName.length > 1) || (inputTaskName[1].isEmpty())) {

                textChannel.sendMessage("No task name has set!").queue();

            } else {

                dateTime = MoveMemberTime.getTime().atDate(LocalDate.now());
                String dateTimeString = dateTime.format(DateTimeFormatter.ofPattern("HH:mm"));

                long period = LocalDateTime.now().until(dateTime, ChronoUnit.SECONDS);

                ScheduledTaskConfig taskConfig = new ScheduledTaskConfig(inputTaskName[1],
                        VoiceService.getSourceName(), VoiceService.getTargetName(), dateTimeString, true);
                taskConfig.setServerID(guild.getId());
                taskConfig.setExceptMember(mapExceptionMember);

                ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
                executorRef = executor;

                futureTask = executor.scheduleAtFixedRate(new MoveCronTask(taskConfig), period, dayInSeconds, TimeUnit.SECONDS);

                // recover data from mongo database
                Document readDocument = CRUD.read(taskConfig);

                // compare data from database with data from class
                if (readDocument != null) {

                    // if different, insert updated data into mongo database
                    CRUD.update(taskConfig, readDocument);
                }
                // if data not exist, create new data
                else {

                    CRUD.create(taskConfig);
                }

                textChannel.sendMessage("Move Member task is running!").queue();

            }
        }
    }
}
