package com.bot.database;

import com.bot.entities.ScheduledTaskConfig;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CRUD {
    static MongoCollection<Document> collection = Connection.database.getCollection("scheduledTasks");
    private static Document foundDoc;
    public static void create(ScheduledTaskConfig task){

        Document entry = new Document("ServerID", task.getServerID());
        entry.append("TaskName", task.getName());
        entry.append("Source", task.getSourceName());
        entry.append("Target", task.getTargetName());
        entry.append("TaskTime", task.getTaskTime());
        entry.append("ExceptMembers", task.getExceptMember());
        entry.append("LogTime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        collection.insertOne(entry);

    }

    public static Document read(ScheduledTaskConfig task){

        foundDoc = collection.find(new Document("TaskName", task.getName())).first();
        return foundDoc;

    }
    public static void update(ScheduledTaskConfig task){

        Document updatedDoc = new Document("Source", task.getSourceName());
        updatedDoc.append("Target", task.getTargetName());
        updatedDoc.append("TaskTime", task.getTaskTime());
        updatedDoc.append("ExceptMembers", task.getExceptMember());
        updatedDoc.append("LogTime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));

        Document replace = new Document("$set", updatedDoc);
        collection.updateOne(foundDoc, replace);

    }

    public static void delete(){



    }
}
