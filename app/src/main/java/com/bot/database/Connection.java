package com.bot.database;

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
    private static final String uri ="mongodb+srv://chesto:26LHEcGoEI4lqr8U@chestocluster.lzsd1se.mongodb.net/?retryWrites=true&w=majority";
    public static void connect() {

        try {
            MongoClient mongoClient = MongoClients.create(uri);
            database = mongoClient.getDatabase("chestoDB");
            database.runCommand(new Document("ping", 1));
            System.out.println("Pinged your deployment. You successfully connected to MongoDB!");

            for (String collection : database.listCollectionNames()) {
                collectionNames.add(collection);
            }
            if (!collectionNames.isEmpty()) {
                if (!collectionNames.contains("scheduledTasks")) {
                    Connection.database.createCollection("scheduledTasks");
                }
            }

        } catch (MongoException e) {
            e.printStackTrace();
        }
    }
}
