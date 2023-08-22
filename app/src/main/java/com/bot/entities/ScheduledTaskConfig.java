package com.bot.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalTime;
import java.util.Map;
import java.util.Objects;

public class ScheduledTaskConfig {

    @JsonProperty(value = "_id", access = JsonProperty.Access.READ_ONLY)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object id;
    @JsonProperty("taskName")
    private String name;
    private String serverID;
    private VoiceIDConfig voice;
    private LocalTime taskTime;
    @JsonProperty("exceptMembers")
    private Map<String, String> exceptMember;
    private String lastEdited;
    private boolean active = false;

    public ScheduledTaskConfig() {}

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

    public VoiceIDConfig getVoice() {
        return voice;
    }

    public void setVoice(VoiceIDConfig voice) {
        this.voice = voice;
    }

    public LocalTime getTaskTime() {
        return taskTime;
    }

    public void setTaskTime(LocalTime taskTime) {
        this.taskTime = taskTime;
    }

    public Map<String, String> getExceptMember() {
        return exceptMember;
    }

    public void setExceptMember(Map<String, String> exceptMember) {
        this.exceptMember = exceptMember;
    }

    public String getLastEdited() { return lastEdited; }

    public void setLastEdited(String lastEdited) {
        this.lastEdited = lastEdited;
    }

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
