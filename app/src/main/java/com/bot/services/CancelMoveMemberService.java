package com.bot.services;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

public class CancelMoveMemberService {

    public void receiveCommand(@NotNull MessageReceivedEvent event) {

        TextChannel textChannel = event.getChannel().asTextChannel();

        String[] inputTaskName = event.getMessage().getContentRaw().split(" ",2);
        if (!(inputTaskName.length > 1) || (inputTaskName[1].isEmpty())) {

            textChannel.sendMessage("Must define task's name to cancel!").queue();

        } else {



            if (RunMoveMemberService.getFutureTask() == null){

                textChannel.sendMessage("No task scheduled!").queue();

            } else {
                // is cancelling the last task assigned
                RunMoveMemberService.getFutureTask().cancel(false);
                //RunMoveMemberTask.getExecutorRef().shutdownNow();
                //ChestoBot.isRunning = false;


                textChannel.sendMessage("Task cancelled!").queue();
            }
        }
    }

}
