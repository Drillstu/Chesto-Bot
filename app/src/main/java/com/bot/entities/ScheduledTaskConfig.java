package com.bot.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;
import java.util.Objects;

public class ScheduledTaskConfig {

    @JsonProperty(value = "_id", access = JsonProperty.Access.READ_ONLY)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object id;
    @JsonProperty("taskName")
    private String name;
    private String serverID;
    @JsonProperty("source")
    private String sourceName;
    @JsonProperty("target")
    private String targetName;
    private String taskTime;
    @JsonProperty("exceptMembers")
    private Map<String, String> exceptMember;
    private String lastEdited;
    public boolean active = false;

    public ScheduledTaskConfig() {}
    public ScheduledTaskConfig(String name, String sourceName, String targetName, String taskTime, boolean active) {
        this.name = name;
        this.sourceName = sourceName;
        this.targetName = targetName;
        this.taskTime = taskTime;
        this.active = active;
    }


    public Object getId() { return id; }

    public void setId(Object id) { this.id = id; }

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

    public String getLastEdited() { return lastEdited; }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
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
