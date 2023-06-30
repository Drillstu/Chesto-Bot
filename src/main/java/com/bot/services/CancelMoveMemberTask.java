package main.java.com.bot.services;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

public class CancelMoveMemberTask {

    public void receiveCommand(@NotNull MessageReceivedEvent event) {

        TextChannel textChannel = event.getChannel().asTextChannel();

        if (RunMoveMemberTask.getFutureTask() == null){

            textChannel.sendMessage("No task scheduled!").queue();

        } else {

            RunMoveMemberTask.getFutureTask().cancel(false);
            RunMoveMemberTask.getExecutorRef().shutdownNow();

            textChannel.sendMessage("Task cancelled!").queue();
        }
    }

}
