package com.bot.database;

import com.bot.utils.Files;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class Connection {
    public static List<String> collectionNames = new ArrayList<>();
    public static MongoDatabase database;
    public static void connect() {

        try {
            Files.readURIData();
            MongoClient mongoClient = MongoClients.create(Files.uri);
            database = mongoClient.getDatabase("chestoDB");
            database.runCommand(new Document("ping", 1));
            System.out.println("Pinged your deployment. You successfully connected to MongoDB!");

            for (String collection : database.listCollectionNames()) {
                collectionNames.add(collection);
            }

            if (!collectionNames.contains("scheduledTasks")) {
                database.createCollection("scheduledTasks");
            }

            if (!collectionNames.contains("jobs")) {
                database.createCollection("jobs");
            }



        } catch (MongoException e) {
            e.printStackTrace();
        }
    }
}
