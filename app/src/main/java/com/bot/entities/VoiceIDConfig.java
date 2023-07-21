package com.bot.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VoiceIDConfig {

    @JsonProperty("source")
    private String sourceName;
    private long sourceID;
    @JsonProperty("target")
    private String targetName;
    private long targetID;

    public VoiceIDConfig() {}

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public long getSourceID() {
        return sourceID;
    }

    public void setSourceID(long sourceID) {
        this.sourceID = sourceID;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public long getTargetID() {
        return targetID;
    }

    public void setTargetID(long targetID) {
        this.targetID = targetID;
    }
}
