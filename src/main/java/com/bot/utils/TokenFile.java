package main.java.com.bot.utils;

import java.io.*;

public class TokenFile {

    public static String token;
    public static void readFile() {

        String currentLine;
        FileReader file;
        BufferedReader reader;

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
}
