package com.bot.utils;

import com.bot.entities.ScheduledTaskConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.Document;

public class DocumentToObject {

    public static Object toObject(Document doc) throws JsonProcessingException {
        // Document to JSON
        String jsonDoc = doc.toJson();
        ObjectMapper mapper = new ObjectMapper();
        // JSON to Object
        return mapper.readValue(jsonDoc, new TypeReference<ScheduledTaskConfig>() {
        });
    }
}
