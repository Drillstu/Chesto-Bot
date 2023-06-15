package services;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.time.LocalTime;

public class MoveMemberTime {

    static LocalTime time;
    public static LocalTime getTime(){
        return time;
    }
    public static void setTime(int hour, int minute){
        time = LocalTime.of(hour, minute);
    }

    public void receiveCommand(@NotNull MessageReceivedEvent event) {

        TextChannel textChannel = event.getChannel().asTextChannel();

        String[] inputTime = event.getMessage().getContentRaw().split(" ",2);
        String[] timeString = inputTime[1].split(":");

        if (!inputTime[1].matches("^(?:[01]?\\d|2[0-3])(?::[0-5]\\d){1,2}$")) {
            textChannel.sendMessage("Incorrect time entered! [pattern: HH:mm]").queue();
        } else {
            int hour = Integer.parseInt(timeString[0]);
            int minute = Integer.parseInt(timeString[1]);
            setTime(hour, minute);
            textChannel.sendMessage("Time set!").queue();
        }
    }
}
