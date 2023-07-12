package com.bot.services;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddExceptionService {
    public static final Map<String, String> mapExceptionMember = new HashMap<>();

    public void receiveCommand(@NotNull MessageReceivedEvent event) {

        TextChannel textChannel = event.getChannel().asTextChannel();

        String[] inputMember = event.getMessage().getContentRaw().split(" ",2);
        if (inputMember.length > 1) {
            List<Member> guildMembers = event.getGuild().getMembersByEffectiveName(inputMember[1], false);
            if (!guildMembers.isEmpty()) {
                mapExceptionMember.put(inputMember[1], inputMember[1]);
                textChannel.sendMessage((inputMember[1]) + " added to exception list!").queue();
            } else {
                textChannel.sendMessage(inputMember[1] + " isn't in the server!").queue();
            }
        } else {
            textChannel.sendMessage("Input a member name to exception list!").queue();
        }
    }

    /*
    public void receiveCommand(@NotNull MessageReceivedEvent event) {

        TextChannel textChannel = event.getChannel().asTextChannel();

        String[] inputMember = event.getMessage().getContentRaw().split(" ",2);
        if (inputMember.length > 1) {
            if (mapExceptionMember.remove(inputMember[1], inputMember[1])) {
                textChannel.sendMessage((inputMember[1]) + " removed to exception list!").queue();
            } else {
                textChannel.sendMessage(inputMember[1] + " isn't in the list!").queue();
            }
        } else {
            textChannel.sendMessage("Input a member name to remove from the list!").queue();
        }
    }

     */
}
