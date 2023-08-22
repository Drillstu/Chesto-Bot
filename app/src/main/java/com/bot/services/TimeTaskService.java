package com.bot.services;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.time.LocalTime;

import static com.bot.services.CreateTaskService.getCreatedTaskConfig;
import static com.bot.services.SelectTaskService.getSelectedTaskConfig;

public class TimeTaskService {

    private static LocalTime time;
    public static LocalTime getTime(){
        return time;
    }
    public static void setTime(int hour, int minute){
        time = LocalTime.of(hour, minute);
    }

    public void receiveCommand(@NotNull MessageReceivedEvent event) {

        TextChannel textChannel = event.getChannel().asTextChannel();

        if (getCreatedTaskConfig() != null || getSelectedTaskConfig() != null) {

            String[] inputTime = event.getMessage().getContentRaw().split(" ", 2);
            String[] timeString = inputTime[1].split(":");

            if (!inputTime[1].matches("^(?:[01]?\\d|2[0-3])(?::[0-5]\\d){1,2}$")) {

                textChannel.sendMessage("Incorrect time entered! [pattern: HH:mm]").queue();

            } else {

                int hour = Integer.parseInt(timeString[0]);
                int minute = Integer.parseInt(timeString[1]);
                setTime(hour, minute);

                if (getCreatedTaskConfig() != null) {
                    getCreatedTaskConfig().setTaskTime(time);
                } else {
                    getSelectedTaskConfig().setTaskTime(time);
                }
                textChannel.sendMessage("Time set!").queue();
            }

        } else {

            textChannel.sendMessage("Must use 'Create' or 'Retrieve' command first!").queue();

        }
    }
}
