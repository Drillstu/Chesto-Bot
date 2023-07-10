package com.bot.utils;

import java.io.*;

public class Files {

    public static String token;
    public static String uri;
    static String currentLine = null;
    static FileReader file;
    static BufferedReader reader;
    public static void readToken() {

        try {
            file = new FileReader("token.txt");
            reader = new BufferedReader(file);

            while ((currentLine = reader.readLine()) != null) {
                String[] line = currentLine.split("token=");
                token = line[1];
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void readURIData() {

        try {
            file = new FileReader("db.txt");
            reader = new BufferedReader(file);

            while ((currentLine = reader.readLine()) != null) {
                String[] line = currentLine.split("uri=");
                uri = line[1];
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

    }
}
