package com.bot.utils;

import com.bot.entities.ScheduledTaskConfig;

public class TaskInfo {

    public static String returnInfo(ScheduledTaskConfig task){

            String exceptions = (task.getExceptMember() == null || !task.getExceptMember().isEmpty()) ?
                    "There are no exceptions registered" : task.getExceptMember().values().toString();

        return "```\n" +
                "Task Name: " + task.getName() + "\n" +
                "Active?: " + (task.getActive() ? "YES" : "NO") + "\n" +
                "Source Channel: " + task.getVoice().getSourceName() + "\n" +
                "Target Channel: " + task.getVoice().getTargetName() + "\n" +
                "Time Set: " + task.getTaskTime() + "\n" +
                "Exceptions: " + exceptions + "\n" +
                "Last Edited: " + task.getLastEdited() + "\n" +
                "```";

    }

}
