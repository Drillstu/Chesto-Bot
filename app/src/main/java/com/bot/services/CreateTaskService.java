package com.bot.services;

import com.bot.database.CRUD;
import com.bot.entities.ScheduledTaskConfig;
import com.bot.entities.VoiceIDConfig;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;

import static com.bot.services.SelectTaskService.selected;
import static com.bot.services.VoiceTaskService.*;

public class CreateTaskService {
    private static ScheduledTaskConfig createdTaskConfig;
    private static VoiceIDConfig createdVoiceConfig;
    public static ScheduledTaskConfig getCreatedTaskConfig() {
        return createdTaskConfig;
    }
    public static VoiceIDConfig getCreatedVoiceConfig() {
        return createdVoiceConfig;
    }

    public void receiveCommand(@NotNull MessageReceivedEvent event) {

        TextChannel textChannel = event.getChannel().asTextChannel();

        String[] inputTaskName = event.getMessage().getContentRaw().split(" ",2);
        if (!(inputTaskName.length > 1) || (inputTaskName[1].isEmpty())) {

            textChannel.sendMessage("No task name has set!").queue();

        } else {

            Document readDocument = CRUD.read(inputTaskName[1]);

            if (readDocument != null) {

                textChannel.sendMessage("Task " + inputTaskName[1] + " already exist!").queue();

            } else {
                selected = false;

                createdVoiceConfig = new VoiceIDConfig();
                createdVoiceConfig.setSourceID(getSourceID());
                createdVoiceConfig.setSourceName(getSourceName());
                createdVoiceConfig.setTargetID(getTargetID());
                createdVoiceConfig.setTargetName(getTargetName());

                createdTaskConfig = new ScheduledTaskConfig();
                createdTaskConfig.setServerID(event.getGuild().getId());
                createdTaskConfig.setName(inputTaskName[1]);
                createdTaskConfig.setVoice(createdVoiceConfig);
                createdTaskConfig.setActive(false);

                textChannel.sendMessage("Task " + createdTaskConfig.getName() + " has created!").queue();
            }
        }
    }
}