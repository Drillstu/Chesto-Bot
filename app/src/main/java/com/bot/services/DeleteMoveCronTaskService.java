package com.bot.services;

import com.bot.database.CRUD;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

public class DeleteMoveCronTaskService {

    public void receiveCommand(@NotNull MessageReceivedEvent event) {

        TextChannel textChannel = event.getChannel().asTextChannel();

        String[] inputName = event.getMessage().getContentRaw().split(" ",2);
        if (inputName.length > 1) {
            if (CRUD.read(inputName[1]) != null) {
                textChannel.sendMessage(inputName[1] + " task has been deleted!").queue();
                CRUD.delete(inputName[1]);
            } else {
                textChannel.sendMessage("Task '" + inputName[1] + "' doesn't exist!").queue();
            }
        } else {
            textChannel.sendMessage("Input a task name !").queue();
        }
    }

}
