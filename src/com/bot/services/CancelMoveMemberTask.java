package com.bot.services;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import static com.bot.services.RunMoveMemberTask.getExecutorRef;
import static com.bot.services.RunMoveMemberTask.getFutureTask;

public class CancelMoveMemberTask {

    public void receiveCommand(@NotNull MessageReceivedEvent event) {

        TextChannel textChannel = event.getChannel().asTextChannel();

        if (getFutureTask() == null){

            textChannel.sendMessage("No task scheduled!").queue();

        } else {

            getFutureTask().cancel(false);
            getExecutorRef().shutdownNow();

            textChannel.sendMessage("Task cancelled!").queue();
        }
    }

}
