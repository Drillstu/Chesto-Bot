package com.bot.services;

import com.bot.database.CRUD;
import com.bot.entities.ScheduledTaskConfig;
import com.bot.utils.TaskInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;

public class RetrieveTaskService {

    public void receiveCommand(@NotNull MessageReceivedEvent event) throws JsonProcessingException {

        TextChannel textChannel = event.getChannel().asTextChannel();
        String[] inputTaskName = event.getMessage().getContentRaw().split(" ", 2);

        if (!(inputTaskName.length > 1) || (inputTaskName[1].isEmpty())) {

            textChannel.sendMessage("Please provide a task name to search!").queue();

        } else {

            Document readDocument = CRUD.read(inputTaskName[1]);

            if (readDocument != null) {

                String json = readDocument.toJson();
                ObjectMapper mapper = new ObjectMapper();
                ScheduledTaskConfig retrievedDoc = mapper.readValue(json, ScheduledTaskConfig.class);

                textChannel.sendMessage(TaskInfo.returnInfo(retrievedDoc)).queue();

            } else {

                textChannel.sendMessage(inputTaskName[1] + " doesn't match!!").queue();

            }

        }
    }
}
