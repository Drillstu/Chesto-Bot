package services;

import main.ChestoBot;
import tasks.MoveMember;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class RunMoveMemberTask {
    // TODO: 13/06/2023 more tests should be done with ExecutorService, such as if a task will run repeatedly every day at specific time
    private static Guild guild;
    private final LocalDate today = LocalDate.now();
    private static Future<?> futureTask;
    private static ScheduledExecutorService executorRef;
    private static final long dayInSeconds= 8640;
    public static ScheduledExecutorService getExecutorRef() { return executorRef; }
    public static Future<?> getFutureTask() { return futureTask; }
    public static Guild getGuild() {
        return guild;
    }

    public static String getDay(LocalDate date, Locale locale) {
        DayOfWeek day = date.getDayOfWeek();
        return day.getDisplayName(TextStyle.FULL, locale);
    }

    public void receiveCommand(@NotNull MessageReceivedEvent event) {

        TextChannel textChannel = event.getChannel().asTextChannel();

        guild = event.getGuild();

        if ((SourceVoiceChannel.getSourceID() == 0) || (TargetVoiceChannel.getTargetID() == 0)) {
            textChannel.sendMessage("No source/target voice channel is set!").queue();
        } else if (MoveMemberTime.getTime() == null) {
            textChannel.sendMessage("No time is set!").queue();
        } else {

            LocalDateTime dateTime = MoveMemberTime.getTime().atDate(LocalDate.now());

            long period = LocalDateTime.now().until(dateTime, ChronoUnit.SECONDS);

            ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
            executorRef = executor;

            futureTask = executor.scheduleAtFixedRate(new CheckTimeTask(), period, dayInSeconds, TimeUnit.SECONDS);

            textChannel.sendMessage("Move Member task is running!").queue();
        }
    }
    static class CheckTimeTask implements Runnable {

        @Override
        public void run() {
            if ((!getDay(new RunMoveMemberTask().today, ChestoBot.localeBr).equals("sábado")) &&
                    (!getDay(new RunMoveMemberTask().today, ChestoBot.localeBr).equals("domingo"))) {
                new MoveMember().move(RunMoveMemberTask.getGuild());
            }
        }
    }

}
