package com.bot.utils;

import com.bot.entities.ScheduledTaskConfig;

public class TaskInfo {

    public static String returnInfo(ScheduledTaskConfig task){

        String exceptions = (!task.getExceptMember().isEmpty()) ?
                task.getExceptMember().values().toString() : "There are no exceptions registered";

        return "```\n" +
                "Task Name: " + task.getName() + "\n" +
                "Source Channel: " + task.getSourceName() + "\n" +
                "Target Channel: " + task.getTargetName() + "\n" +
                "Time Set: " + task.getTaskTime() + "\n" +
                "Exceptions: " + exceptions + "\n" +
                "Last Edited: " + task.getLastEdited() + "\n" +
                "```";

    }

}
