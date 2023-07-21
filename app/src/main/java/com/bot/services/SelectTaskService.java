package com.bot.services;

import com.bot.database.CRUD;
import com.bot.entities.ScheduledTaskConfig;
import com.bot.utils.TaskInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;

import static com.bot.services.CreateTaskService.created;
import static com.bot.utils.DocumentToObject.toObject;

public class SelectTaskService {

    private static ScheduledTaskConfig selectedTaskConfig;
    private static Document selectedDocument;
    public static boolean selected = false;
    public static ScheduledTaskConfig getSelectedTaskConfig() {
        return selectedTaskConfig;
    }
    public static Document getSelectedDocument() {
        return selectedDocument;
    }

    public void receiveCommand(@NotNull MessageReceivedEvent event) throws JsonProcessingException {

        TextChannel textChannel = event.getChannel().asTextChannel();
        String[] inputTaskName = event.getMessage().getContentRaw().split(" ", 2);

        if (!(inputTaskName.length > 1) || (inputTaskName[1].isEmpty())) {

            textChannel.sendMessage("Please provide a task name to search!").queue();

        } else {

            if (created) {

                textChannel.sendMessage("There's already a newly created task!").queue();

            } else {

                selectedDocument = CRUD.read(inputTaskName[1]);

                if (selectedDocument != null) {

                    selectedTaskConfig = (ScheduledTaskConfig) toObject(selectedDocument);

                    selected = true;

                    textChannel.sendMessage(selectedTaskConfig.getName() + " task is selected!").queue();
                    textChannel.sendMessage(TaskInfo.returnInfo(selectedTaskConfig)).queue();

                } else {

                    textChannel.sendMessage(inputTaskName[1] + " doesn't match!!").queue();

                }
            }
        }
    }
}
