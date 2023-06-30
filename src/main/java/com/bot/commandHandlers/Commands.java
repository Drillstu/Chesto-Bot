package main.java.com.bot.commandHandlers;

public enum Commands {

    ADD_SOURCE("sourceVoice"),
    ADD_TARGET("targetVoice"),
    SET_TIME("setTime"),
    ADD_EXCEPTION("addException"),
    REMOVE_EXCEPTION("removeException"),
    RUN_TASK("run"),
    CANCEL_TASK("cancel"),
    PING("ping");

    private final String inputCommand;
    Commands(String inputCommand) {
        this.inputCommand = inputCommand;
    }
    public String getInputCommand(){
        return inputCommand;
    }

}
