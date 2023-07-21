package com.bot.application;

import com.bot.commandHandlers.MessageListener;
import com.bot.database.Connection;
import com.bot.utils.Files;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

public class ChestoBot {

    public static final Locale localeBr = new Locale("pt", "BR");
    public static JDA jda;
    public static final Map<Long, Character> prefixMap = new HashMap<>();
    public static Map<String, ScheduledFuture<?>> tasks = new HashMap<>();
    public static void main(String[] args) throws InterruptedException {

        Files.readToken();

        jda = JDABuilder.create(Files.token,
                EnumSet.allOf(GatewayIntent.class)).build();

        jda.addEventListener(new MessageListener());

        Connection.connect();

        for (Guild guild : jda.awaitReady().getGuilds()) {
            prefixMap.put(guild.getIdLong(), '!');

        }
/*
        for (Map.Entry<String, ScheduledFuture<?>> entry : tasks.entrySet()) {
            tasks.put
        }

 */
    }
}
