package com.bot.services;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SourceVoiceChannel {
    private static long sourceID;
    public static long getSourceID(){
        return sourceID;
    }

    public void receiveCommand(@NotNull MessageReceivedEvent event) {

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