package com.bot.services;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.bot.services.CreateTaskService.getCreatedTaskConfig;
import static com.bot.services.SelectTaskService.getSelectedTaskConfig;

public class VoiceTaskService {
    private static long sourceID;
    private static String sourceName;
    private static long targetID;
    private static String targetName;
    public static long getSourceID(){
        return sourceID;
    }
    public static String getSourceName() { return sourceName; }
    public static long getTargetID(){
        return targetID;
    }
    public static String getTargetName() { return targetName; }

    public void receiveSource(@NotNull MessageReceivedEvent event) {

        boolean existChannelName = false;
        TextChannel textChannel = event.getChannel().asTextChannel();
        Guild guild = event.getGuild();

        if (getCreatedTaskConfig() != null || getSelectedTaskConfig() != null) {

            String[] inputChannel = event.getMessage().getContentRaw().split(" ", 2);
            if (inputChannel.length > 1) {
                List<VoiceChannel> voiceChannels = guild.getVoiceChannelsByName(inputChannel[1], false);
                for (VoiceChannel vc : voiceChannels) {
                    if ((vc != null) && (vc.getName().equals(inputChannel[1]))) {
                        textChannel.sendMessage(vc.getName() + " has added as source channel!").queue();
                        sourceID = vc.getIdLong();
                        sourceName = vc.getName();

                        //sourceMap.put(sourceID, sourceName);

                        if (getCreatedTaskConfig() != null) {
                            getCreatedTaskConfig().getVoice().setSourceID(sourceID);
                            getCreatedTaskConfig().getVoice().setSourceName(sourceName);
                        } else {
                            getSelectedTaskConfig().getVoice().setSourceID(sourceID);
                            getSelectedTaskConfig().getVoice().setSourceName(sourceName);
                        }

                        existChannelName = true;
                    }
                }
                if (!existChannelName) {
                    textChannel.sendMessage(inputChannel[1] + " channel does not exist in the server!").queue();
                }
            } else {
                textChannel.sendMessage("Input a voice channel name!").queue();
            }
        } else {

            textChannel.sendMessage("Must use 'Create' or 'Retrieve' command first!").queue();

        }
    }

    public void receiveTarget(@NotNull MessageReceivedEvent event) {

        boolean existChannelName = false;
        TextChannel textChannel = event.getChannel().asTextChannel();
        Guild guild = event.getGuild();

        if (getCreatedTaskConfig() != null || getSelectedTaskConfig() != null) {

            String[] inputChannel = event.getMessage().getContentRaw().split(" ", 2);
            if (inputChannel.length > 1) {
                List<VoiceChannel> voiceChannels = guild.getVoiceChannelsByName(inputChannel[1], false);
                for (VoiceChannel vc : voiceChannels) {
                    if ((vc != null) && (vc.getName().equals(inputChannel[1]))) {
                        textChannel.sendMessage(vc.getName() + " has added as target channel!").queue();
                        targetID = vc.getIdLong();
                        targetName = vc.getName();

                        //targetMap.put(targetID, targetName);

                        if (getCreatedTaskConfig() != null) {
                            getCreatedTaskConfig().getVoice().setTargetID(targetID);
                            getCreatedTaskConfig().getVoice().setTargetName(targetName);
                        } else {
                            getSelectedTaskConfig().getVoice().setTargetID(targetID);
                            getSelectedTaskConfig().getVoice().setTargetName(targetName);
                        }

                        existChannelName = true;
                    }
                }
                if (!existChannelName) {
                    textChannel.sendMessage(inputChannel[1] + " channel does not exist in the server!").queue();
                }
            } else {
                textChannel.sendMessage("Input a voice channel name!").queue();
            }
        } else {

            textChannel.sendMessage("Must use 'Create' or 'Retrieve' command first!").queue();

        }
    }
}