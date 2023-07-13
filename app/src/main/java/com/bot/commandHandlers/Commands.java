package com.bot.commandHandlers;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import static com.bot.application.ChestoBot.prefixMap;

public enum Commands {
    HELP("help", "show all commands' descriptions"),
    ADD_SOURCE("sourceVoice", "add source voice channel"),
    ADD_TARGET("targetVoice", "add target voice channel"),
    SET_TIME("setTime", "define time at which task will play"),
    ADD_EXCEPTION("addException", "add user to exception list"),
    REMOVE_EXCEPTION("removeException", "remove user from exception list"),
    RUN_TASK("run", "schedule task"),
    CANCEL_TASK("cancel", "cancel scheduled task"),
    DELETE_TASK("delete", "delete task"),
    PING("ping", "measure connection with the bot");

    private final String inputCommand;
    private final String description;

    Commands(String inputCommand, String description) {
        this.inputCommand = inputCommand;
        this.description = description;
    }

    public static String help(@NotNull MessageReceivedEvent event){
        char prefix = prefixMap.get(event.getGuild().getIdLong());
        return "ChestoBot commands: \n\n" +
                prefix + Commands.ADD_SOURCE.getInputCommand() + ": " + Commands.ADD_SOURCE.getDescription() + "\n" +
                prefix + Commands.ADD_TARGET.getInputCommand() + ": " + Commands.ADD_TARGET.getDescription() + "\n" +
                prefix + Commands.SET_TIME.getInputCommand() + ": " + Commands.SET_TIME.getDescription() + "\n" +
                prefix + Commands.ADD_EXCEPTION.getInputCommand() + ": " + Commands.ADD_EXCEPTION.getDescription() + "\n" +
                prefix + Commands.REMOVE_EXCEPTION.getInputCommand() + ": " + Commands.REMOVE_EXCEPTION.getDescription() + "\n" +
                prefix + Commands.RUN_TASK.getInputCommand() + ": " + Commands.RUN_TASK.getDescription() + "\n" +
                prefix + Commands.CANCEL_TASK.getInputCommand() + ": " + Commands.CANCEL_TASK.getDescription() + "\n" +
                prefix + Commands.DELETE_TASK.getInputCommand() + ": " + Commands.DELETE_TASK.getDescription() + "\n" +
                prefix + Commands.PING.getInputCommand() + ": " + Commands.PING.getDescription() + "\n";
    }
    public String getInputCommand(){
        return inputCommand;
    }
    public String getDescription(){
        return description;
    }

}
