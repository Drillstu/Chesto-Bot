package com.bot.services;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VoiceService {
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
    Map<String, String> sourceMap = new HashMap<>();
    Map<String, String> targetMap = new HashMap<>();
    public void receiveSource(@NotNull MessageReceivedEvent event) {

        boolean existChannelName = false;
        TextChannel textChannel = event.getChannel().asTextChannel();
        Guild guild = event.getGuild();

        String[] inputChannel = event.getMessage().getContentRaw().split(" ",2);
        if (inputChannel.length > 1) {
            List<VoiceChannel> voiceChannels = guild.getVoiceChannelsByName(inputChannel[1], false);
            for (VoiceChannel vc : voiceChannels) {
                if ((vc != null) && (vc.getName().equals(inputChannel[1]))) {
                    textChannel.sendMessage(vc.getName() + " has added as source channel!").queue();
                    sourceID = vc.getIdLong();
                    sourceName = vc.getName();

                    // insert data into map
                    sourceMap.put(vc.getId(), vc.getName());

                    existChannelName = true;
                }
            }
            if (!existChannelName) {
                textChannel.sendMessage(inputChannel[1] + " channel does not exist in the server!").queue();
            }
        } else {
            textChannel.sendMessage("Input a voice channel name!").queue();
        }
    }

    public void receiveTarget(@NotNull MessageReceivedEvent event) {

        boolean existChannelName = false;
        TextChannel textChannel = event.getChannel().asTextChannel();
        Guild guild = event.getGuild();

        String[] inputChannel = event.getMessage().getContentRaw().split(" ",2);
        if (inputChannel.length > 1) {
            List<VoiceChannel> voiceChannels = guild.getVoiceChannelsByName(inputChannel[1], false);
            for (VoiceChannel vc : voiceChannels) {
                if ((vc != null) && (vc.getName().equals(inputChannel[1]))) {
                    textChannel.sendMessage(vc.getName() + " has added as target channel!").queue();
                    targetID = vc.getIdLong();
                    targetName = vc.getName();

                    // insert data into map
                    targetMap.put(vc.getId(), vc.getName());

                    existChannelName = true;
                }
            }
            if (!existChannelName) {
                textChannel.sendMessage(inputChannel[1] + " channel does not exist in the server!").queue();
            }
        } else {
            textChannel.sendMessage("Input a voice channel name!").queue();
        }
    }
}