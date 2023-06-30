package com.bot.commandHandlers;

import com.bot.events.ReadCommand;
import com.bot.services.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class MessageListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        if (ReadCommand.onMessageReceived(event, Commands.ADD_SOURCE.getInputCommand())) {
            new SourceVoiceChannel().receiveCommand(event);
        }
        else if (ReadCommand.onMessageReceived(event, Commands.ADD_TARGET.getInputCommand())) {
            new TargetVoiceChannel().receiveCommand(event);
        }
        else if (ReadCommand.onMessageReceived(event, Commands.SET_TIME.getInputCommand())) {
            new MoveMemberTime().receiveCommand(event);
        }
        else if (ReadCommand.onMessageReceived(event, Commands.ADD_EXCEPTION.getInputCommand())) {
            new AddExceptionMember().receiveCommand(event);
        }
        else if (ReadCommand.onMessageReceived(event, Commands.REMOVE_EXCEPTION.getInputCommand())) {
            new RemoveExceptionMember().receiveCommand(event);
        }
        else if (ReadCommand.onMessageReceived(event, Commands.RUN_TASK.getInputCommand())) {
            new RunMoveMemberTask().receiveCommand(event);
        }
        else if (ReadCommand.onMessageReceived(event, Commands.CANCEL_TASK.getInputCommand())) {
            new CancelMoveMemberTask().receiveCommand(event);
        }
        else if (ReadCommand.onMessageReceived(event, Commands.PING.getInputCommand())) {
            new Ping().receiveCommand(event);
        }
    }
}
