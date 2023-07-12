package com.bot.services;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import static com.bot.services.AddExceptionService.mapExceptionMember;


public class RemoveExceptionService {

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
}
