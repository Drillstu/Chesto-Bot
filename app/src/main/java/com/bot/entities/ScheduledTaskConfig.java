package com.bot.entities;

import java.util.Map;
import java.util.Objects;

public class ScheduledTaskConfig {

    private String name;
    private String serverID;
    private String sourceName;
    private String targetName;
    private String taskTime;
    private Map<String, String> exceptMember;
    public boolean isRunning = false;

    public ScheduledTaskConfig(String name, String sourceName, String targetName, String taskTime, boolean isRunning) {
        this.name = name;
        this.sourceName = sourceName;
        this.targetName = targetName;
        this.taskTime = taskTime;
        this.isRunning = isRunning;
    }
    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getServerID() {
        return serverID;
    }

    public void setServerID(String serverID) {
        this.serverID = serverID;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public String getTaskTime() {
        return taskTime;
    }

    public void setTaskTime(String taskTime) {
        this.taskTime = taskTime;
    }

    public Map<String, String> getExceptMember() {
        return exceptMember;
    }

    public void setExceptMember(Map<String, String> exceptMember) {
        this.exceptMember = exceptMember;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScheduledTaskConfig that = (ScheduledTaskConfig) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
