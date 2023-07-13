package com.bot.database;

import com.bot.entities.ScheduledTaskConfig;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CRUD {
    static MongoCollection<Document> collection = Connection.database.getCollection("scheduledTasks");

    public static void create(ScheduledTaskConfig taskConfig){

        Document entry = new Document("ServerID", taskConfig.getServerID());
        entry.append("TaskName", taskConfig.getName());
        entry.append("Source", taskConfig.getSourceName());
        entry.append("Target", taskConfig.getTargetName());
        entry.append("TaskTime", taskConfig.getTaskTime());
        entry.append("ExceptMembers", taskConfig.getExceptMember());
        entry.append("LogTime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        collection.insertOne(entry);

    }

    public static Document read(ScheduledTaskConfig taskConfig){

        return collection.find(new Document("TaskName", taskConfig.getName())).first();

    }

    public static Document read(String taskName){

        return collection.find(new Document("TaskName", taskName)).first();

    }
    public static void update(ScheduledTaskConfig taskConfig, Document oldDoc){

        Document updatedDoc = new Document("Source", taskConfig.getSourceName());
        updatedDoc.append("Target", taskConfig.getTargetName());
        updatedDoc.append("TaskTime", taskConfig.getTaskTime());
        updatedDoc.append("ExceptMembers", taskConfig.getExceptMember());
        updatedDoc.append("LogTime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));

        Document replace = new Document("$set", updatedDoc);
        collection.updateOne(oldDoc, replace);

    }

    public static void delete(String taskName){

        Document deletedDoc = new Document ("TaskName", taskName);

        try {
            collection.deleteOne(deletedDoc);
        } catch (MongoException e) {
            System.out.println("Couldn't delete document! ERROR: " + e);
        }
    }
}
