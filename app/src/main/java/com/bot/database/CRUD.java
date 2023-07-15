package com.bot.database;

import com.bot.entities.ScheduledTaskConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CRUD {
    static MongoCollection<Document> collection = Connection.database.getCollection("scheduledTasks");
    public static MongoCollection<Document> getCollection() { return collection;}

    public static void create(ScheduledTaskConfig taskConfig) throws JsonProcessingException {

        ObjectWriter writer = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String JSON = writer.writeValueAsString(taskConfig);
        Document entry = Document.parse(JSON);

        // manually generating unique _id
        entry.append("_id", new ObjectId());

        //manually creating log time
        entry.append("lastEdited", LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));

        collection.insertOne(entry);

    }

    public static Document read(ScheduledTaskConfig taskConfig){

        return collection.find(new Document("taskName", taskConfig.getName())).first();

    }

    public static Document read(String taskName){

        return collection.find(new Document("taskName", taskName)).first();

    }
    public static void update(ScheduledTaskConfig taskConfig, Document oldDoc) throws JsonProcessingException {

        String jsonDoc = oldDoc.toJson();
        ObjectMapper mapper = new ObjectMapper();
        ScheduledTaskConfig newTask = mapper.readValue(jsonDoc, ScheduledTaskConfig.class);

        newTask.setId(null);
        newTask.setSourceName(taskConfig.getSourceName());
        newTask.setTargetName(taskConfig.getTargetName());
        newTask.setTaskTime(taskConfig.getTaskTime());
        newTask.setExceptMember(taskConfig.getExceptMember());

        ObjectWriter writer = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String jsonObj = writer.writeValueAsString(newTask);
        Document updatedDoc = Document.parse(jsonObj);

        //manually creating log time
        updatedDoc.append("lastEdited", LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));

        Document replace = new Document("$set", updatedDoc);

        collection.updateOne(oldDoc, replace);

    }

    public static void delete(String taskName){

        Document deletedDoc = new Document ("taskName", taskName);

        try {
            collection.deleteOne(deletedDoc);
        } catch (MongoException e) {
            System.out.println("Couldn't delete document! ERROR: " + e);
        }
    }
}
