package com.bot.services;

import com.bot.database.CRUD;
import com.bot.entities.ScheduledTaskConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;

import static com.bot.tasks.MoveCronTask.stopSchedule;

public class StopTaskService {

    public void receiveCommand(@NotNull MessageReceivedEvent event) throws JsonProcessingException {

        TextChannel textChannel = event.getChannel().asTextChannel();

        String[] inputTaskName = event.getMessage().getContentRaw().split(" ",2);
        if (!(inputTaskName.length > 1) || (inputTaskName[1].isEmpty())) {

            textChannel.sendMessage("Must define task's name to stop!").queue();

        } else {

            Document readDocument = CRUD.read(inputTaskName[1]);
            if (readDocument != null) {

                // Document to JSON
                String jsonDoc = readDocument.toJson();
                ObjectMapper mapper = new ObjectMapper();
                // JSON to Object
                ScheduledTaskConfig stopTask = mapper.readValue(jsonDoc, ScheduledTaskConfig.class);

                if (stopTask.getActive()) {
                    stopSchedule(stopTask);

                    // is cancelling the last task assigned
                    //RunTaskService.getFutureTask().cancel(false);
                    //RunMoveMemberTask.getExecutorRef().shutdownNow();

                    // TODO: try create task map to manipulate run/cancel specific task
                    //Future<?> f = ScheduledExecutorService
                    /*
                    ScheduledFuture<?> f = tasks.get(stopTask.getName());
                    f.cancel(false);
                    */
                    stopTask.setActive(false);
                    CRUD.update(stopTask, readDocument);

                    // test purposes
                    System.out.println("stopping task " + stopTask.getName());

                    textChannel.sendMessage("Task " + stopTask.getName() + " stopped!").queue();
                } else {
                    textChannel.sendMessage("Task " + stopTask.getName() + " is already stopped!").queue();
                }
            } else {
                textChannel.sendMessage("No such task name currently exist!").queue();
            }
        }
    }
}
