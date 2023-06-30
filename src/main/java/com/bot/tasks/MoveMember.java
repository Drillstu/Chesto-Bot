package main.java.com.bot.tasks;

import main.java.com.bot.services.SourceVoiceChannel;
import main.java.com.bot.services.TargetVoiceChannel;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import static main.java.com.bot.services.AddExceptionMember.mapMember;

public class MoveMember extends ListenerAdapter {
    public void move(Guild guild){

        VoiceChannel sourceChannel = guild.getVoiceChannelById(SourceVoiceChannel.getSourceID());
        VoiceChannel targetChannel = guild.getVoiceChannelById(TargetVoiceChannel.getTargetID());

        int sourceChannelJoinedMembers = sourceChannel != null ? sourceChannel.getMembers().size() : 0;

        if (sourceChannelJoinedMembers > 0) {
            sourceChannel.getMembers().stream().filter(
                    m -> !mapMember.containsKey(m.getUser().getEffectiveName())).forEach(m -> {
                m.getGuild().moveVoiceMember(m, targetChannel).queue();
            });
        }
    }
}
