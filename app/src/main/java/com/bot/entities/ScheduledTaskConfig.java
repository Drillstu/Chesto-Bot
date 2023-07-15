package com.bot.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;
import java.util.Objects;

public class ScheduledTaskConfig {

    @JsonProperty(value = "_id", access = JsonProperty.Access.READ_ONLY)
    private Object id;
    @JsonProperty("TaskName")
    private String name;
    @JsonProperty("ServerID")
    private String serverID;
    @JsonProperty("Source")
    private String sourceName;
    @JsonProperty("Target")
    private String targetName;
    @JsonProperty("TaskTime")
    private String taskTime;
    @JsonProperty("ExceptMembers")
    private Map<String, String> exceptMember;
    @JsonProperty("LogTime")
    private String logTime;
    public boolean isRunning = false;

    public ScheduledTaskConfig() {}
    public ScheduledTaskConfig(String name, String sourceName, String targetName, String taskTime, boolean isRunning) {
        this.name = name;
        this.sourceName = sourceName;
        this.targetName = targetName;
        this.taskTime = taskTime;
        this.isRunning = isRunning;
    }
    public Object getId() { return id; }

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

    public String getLogTime() { return logTime; }

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
