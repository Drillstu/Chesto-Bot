package com.bot.commandHandlers;

import com.bot.events.ReadCommand;
import com.bot.services.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.quartz.SchedulerException;

import java.io.IOException;

public class MessageListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        if (ReadCommand.onMessageReceived(event, Commands.HELP.getInputCommand())) {
            event.getChannel().asTextChannel().sendMessage(Commands.help(event)).queue();
        }
        else if (ReadCommand.onMessageReceived(event, Commands.ADD_SOURCE.getInputCommand())) {
            new VoiceTaskService().receiveSource(event);
        }
        else if (ReadCommand.onMessageReceived(event, Commands.ADD_TARGET.getInputCommand())) {
            new VoiceTaskService().receiveTarget(event);
        }
        else if (ReadCommand.onMessageReceived(event, Commands.SET_TIME.getInputCommand())) {
            new TimeTaskService().receiveCommand(event);
        }
        else if (ReadCommand.onMessageReceived(event, Commands.ADD_EXCEPTION.getInputCommand())) {
            new ExceptionTaskService().addCommand(event);
        }
        else if (ReadCommand.onMessageReceived(event, Commands.REMOVE_EXCEPTION.getInputCommand())) {
            new ExceptionTaskService().removeCommand(event);
        }
        else if (ReadCommand.onMessageReceived(event, Commands.CREATE_TASK.getInputCommand())) {
            new CreateTaskService().receiveCommand(event);
        }
        else if (ReadCommand.onMessageReceived(event, Commands.SELECT_TASK.getInputCommand())) {
            try {
                new SelectTaskService().selectCommand(event);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        else if (ReadCommand.onMessageReceived(event, Commands.SAVE_TASK.getInputCommand())) {
            try {
                new SaveTaskService().receiveCommand(event);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        else if (ReadCommand.onMessageReceived(event, Commands.RUN_TASK.getInputCommand())) {
            try {
                new RunTaskService().receiveCommand(event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else if (ReadCommand.onMessageReceived(event, Commands.STOP_TASK.getInputCommand())) {
            try {
                new StopTaskService().receiveCommand(event);
            } catch (JsonProcessingException | SchedulerException e) {
                throw new RuntimeException(e);
            }
        }

        else if (ReadCommand.onMessageReceived(event, Commands.DELETE_TASK.getInputCommand())) {
            new DeleteTaskService().receiveCommand(event);
        }
        else if (ReadCommand.onMessageReceived(event, Commands.PING.getInputCommand())) {
            new PingService().receiveCommand(event);
        }
    }
}
