package com.bot.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.quartz.JobKey;

import java.io.IOException;

public class JobKeyDeserializer extends JsonDeserializer<JobKey> {

    @Override
    public JobKey deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);
        // Parse the JSON node and create a JobKey instance manually
        String name = node.get("name").asText();
        String group = node.get("group").asText();
        return new JobKey(name, group);
    }

}
