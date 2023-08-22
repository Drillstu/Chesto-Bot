package com.bot.database;

import com.bot.entities.ScheduledTaskConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.bot.database.Connection.database;
import static com.bot.utils.DocumentToObject.toObject;
import static com.bot.utils.ObjectToDocument.toDocument;

public class CRUD {
    static MongoCollection<Document> collection = database.getCollection("scheduledTasks");

    public static void create(ScheduledTaskConfig taskConfig) throws JsonProcessingException {

        Document entry = toDocument(taskConfig);

        // manually generating unique _id
        entry.append("_id", new ObjectId());

        //manually creating log time
        entry.append("lastEdited", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));

        collection.insertOne(entry);

    }

    public static Document read(String taskName){

        return collection.find(new Document("taskName", taskName)).first();

    }

    public static Document read(String taskName, String collection){

        return database.getCollection(collection).find(new Document("taskName", taskName)).first();

    }

    public static void update(ScheduledTaskConfig taskConfig, Document oldDoc) throws JsonProcessingException {

        ScheduledTaskConfig newTask = (ScheduledTaskConfig) toObject(oldDoc);

        newTask.setId(null);
        //newTask.setSourceVoice(taskConfig.getSourceVoice());
        //newTask.setTargetVoice(taskConfig.getTargetVoice());
        newTask.setVoice(taskConfig.getVoice());
        newTask.setTaskTime(taskConfig.getTaskTime());
        newTask.setExceptMember(taskConfig.getExceptMember());
        newTask.setActive(taskConfig.getActive());

        Document updatedDoc = toDocument(newTask);

        //manually creating log time
        updatedDoc.append("lastEdited", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));

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
