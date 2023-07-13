package com.bot.commandHandlers;

import com.bot.events.ReadCommand;
import com.bot.services.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class MessageListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        if (ReadCommand.onMessageReceived(event, Commands.ADD_SOURCE.getInputCommand())) {
            new SourceVoiceService().receiveCommand(event);
        }
        else if (ReadCommand.onMessageReceived(event, Commands.ADD_TARGET.getInputCommand())) {
            new TargetVoiceService().receiveCommand(event);
        }
        else if (ReadCommand.onMessageReceived(event, Commands.SET_TIME.getInputCommand())) {
            new MoveMemberTime().receiveCommand(event);
        }
        else if (ReadCommand.onMessageReceived(event, Commands.ADD_EXCEPTION.getInputCommand())) {
            new ExceptionService().addCommand(event);
        }
        else if (ReadCommand.onMessageReceived(event, Commands.REMOVE_EXCEPTION.getInputCommand())) {
            new ExceptionService().removeCommand(event);
        }
        else if (ReadCommand.onMessageReceived(event, Commands.RUN_TASK.getInputCommand())) {
            try {
                new RunMoveMemberService().receiveCommand(event);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        else if (ReadCommand.onMessageReceived(event, Commands.CANCEL_TASK.getInputCommand())) {
            new CancelMoveMemberService().receiveCommand(event);
        }
        else if (ReadCommand.onMessageReceived(event, Commands.PING.getInputCommand())) {
            new PingService().receiveCommand(event);
        }
    }
}
