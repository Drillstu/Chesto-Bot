package services;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import static services.RunMoveMemberTask.getExecutorRef;
import static services.RunMoveMemberTask.getFutureTask;


public class CancelMoveMemberTask {

    public void receiveCommand(@NotNull MessageReceivedEvent event) {

        TextChannel textChannel = event.getChannel().asTextChannel();

        getFutureTask().cancel(false);
        getExecutorRef().shutdownNow();

        textChannel.sendMessage("Task cancelled!").queue();
    }

}
