package com.bot.application;

import com.bot.commandHandlers.MessageListener;
import com.bot.database.Connection;
import com.bot.utils.Files;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.quartz.JobKey;
import org.quartz.Scheduler;

import java.io.IOException;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

public class ChestoBot {

    public static final Locale localeBr = new Locale("pt", "BR");
    public static JDA jda;
    public static final Map<Long, Character> prefixMap = new HashMap<>();
    public static HashMap<String, ScheduledFuture<?>> tasks = new HashMap<>();
    public static HashMap<String, JobKey> jobs = new HashMap<>();
    public static Scheduler scheduler;
    public static void main(String[] args) throws InterruptedException, IOException {

        Files.readToken();

        jda = JDABuilder.create(Files.token,
                EnumSet.allOf(GatewayIntent.class)).build();

        jda.addEventListener(new MessageListener());

        Connection.connect();

        for (Guild guild : jda.awaitReady().getGuilds()) {
            prefixMap.put(guild.getIdLong(), '!');
        }
    }
}
