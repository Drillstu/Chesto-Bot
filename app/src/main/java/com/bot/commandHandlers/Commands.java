package com.bot.commandHandlers;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import static com.bot.application.ChestoBot.prefixMap;

public enum Commands {
    // TODO: edit (and save changes?) task command
    // TODO: create save command (?) and take save action from run command out (?)
    HELP("help", "show all commands' descriptions"),
    ADD_SOURCE("sourceVoice \"Source Channel\"", "add source voice channel"),
    ADD_TARGET("targetVoice \"Target Channel\"", "add target voice channel"),
    SET_TIME("setTime \"12:34\"", "define time at which task will play"),
    ADD_EXCEPTION("addException \"John\"", "add user to exception list"),
    REMOVE_EXCEPTION("removeException \"John\"", "remove user from exception list"),
    CREATE_TASK("create \"Task Name\"", "create new task"),
    SELECT_TASK("select \"Task Name\"", "retrieve task info"),
    SAVE_TASK("save", "save task"),
    RUN_TASK("run \"Task Name\"", "schedule task"),
    STOP_TASK("stop \"Task Name\"", "cancel scheduled task"),
    DELETE_TASK("delete \"Task Name\"", "delete task"),
    PING("ping", "measure connection with the bot");

    private final String inputCommand;
    private final String description;

    Commands(String inputCommand, String description) {
        this.inputCommand = inputCommand;
        this.description = description;
    }

    public static String help(@NotNull MessageReceivedEvent event){
        char prefix = prefixMap.get(event.getGuild().getIdLong());
        return "ChestoBot commands: \n" +
                "```\n" +
                prefix + Commands.ADD_SOURCE.getInputCommand() + ": " + Commands.ADD_SOURCE.getDescription() + "\n" +
                prefix + Commands.ADD_TARGET.getInputCommand() + ": " + Commands.ADD_TARGET.getDescription() + "\n" +
                prefix + Commands.SET_TIME.getInputCommand() + ": " + Commands.SET_TIME.getDescription() + "\n" +
                prefix + Commands.ADD_EXCEPTION.getInputCommand() + ": " + Commands.ADD_EXCEPTION.getDescription() + "\n" +
                prefix + Commands.REMOVE_EXCEPTION.getInputCommand() + ": " + Commands.REMOVE_EXCEPTION.getDescription() + "\n" +
                prefix + Commands.CREATE_TASK.getInputCommand() + ": " + Commands.CREATE_TASK.getDescription() + "\n" +
                prefix + Commands.SELECT_TASK.getInputCommand() + ": " + Commands.SELECT_TASK.getDescription() + "\n" +
                prefix + Commands.SAVE_TASK.getInputCommand() + ": " + Commands.SAVE_TASK.getDescription() + "\n" +
                prefix + Commands.RUN_TASK.getInputCommand() + ": " + Commands.RUN_TASK.getDescription() + "\n" +
                prefix + Commands.STOP_TASK.getInputCommand() + ": " + Commands.STOP_TASK.getDescription() + "\n" +
                prefix + Commands.DELETE_TASK.getInputCommand() + ": " + Commands.DELETE_TASK.getDescription() + "\n" +
                prefix + Commands.PING.getInputCommand() + ": " + Commands.PING.getDescription() + "\n" +
                "```";
    }
    public String getInputCommand(){
        return inputCommand;
    }
    public String getDescription(){
        return description;
    }

}
