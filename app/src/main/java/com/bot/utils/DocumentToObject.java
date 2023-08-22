package com.bot.utils;

import com.bot.entities.ScheduledTaskConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.bson.Document;
import org.quartz.JobKey;

public class DocumentToObject {

    public static Object toObject(Document doc) throws JsonProcessingException {
        // Document to JSON
        String jsonDoc = doc.toJson();
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        // JSON to Object
        return mapper.readValue(jsonDoc, new TypeReference<ScheduledTaskConfig>() {
        });
    }

    public static JobKey toJobKey(Document doc) throws JsonProcessingException {
        // Document to JSON
        String jsonDoc = doc.toJson();
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        // JSON to Object
        return mapper.readValue(jsonDoc, JobKey.class);
    }
}
