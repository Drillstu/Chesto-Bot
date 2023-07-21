package com.bot.services;

import com.bot.database.CRUD;
import com.bot.utils.TaskInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.stream.Stream;

import static com.bot.services.CreateTaskService.getCreatedTaskConfig;
import static com.bot.services.SelectTaskService.*;

public class SaveTaskService {

    public void receiveCommand(@NotNull MessageReceivedEvent event) throws JsonProcessingException {

        TextChannel textChannel = event.getChannel().asTextChannel();

        if (getCreatedTaskConfig() != null) {

            if (!checkTaskProps()) {
                CRUD.create(getCreatedTaskConfig());
                textChannel.sendMessage("Task " + getCreatedTaskConfig().getName() + " has created!").queue();
                textChannel.sendMessage(TaskInfo.returnInfo(getCreatedTaskConfig())).queue();
                selected = true;
            } else {
                textChannel.sendMessage("Time/Voice Channels must be set!").queue();
            }

        } else if (getSelectedTaskConfig() != null) {
            CRUD.update(getSelectedTaskConfig(), getSelectedDocument());
            textChannel.sendMessage("Task " + getSelectedTaskConfig().getName() + " has updated!").queue();
        } else {
            textChannel.sendMessage("Must use 'Create' or 'Retrieve' command first!").queue();
        }
    }

    public boolean checkTaskProps() {
        String sourceProp = getCreatedTaskConfig().getVoice().getSourceName();
        String targetProp = getCreatedTaskConfig().getVoice().getTargetName();
        String timeProp = getCreatedTaskConfig().getTaskTime();
        return Stream.of(sourceProp, targetProp, timeProp).anyMatch(Objects::isNull);
    }
}
