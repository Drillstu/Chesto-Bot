package com.bot.tasks;

import com.bot.application.ChestoBot;
import com.bot.entities.ScheduledTaskConfig;
import com.bot.services.RunTaskService;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static com.bot.application.ChestoBot.tasks;
import static com.bot.services.ExceptionTaskService.mapExceptionMember;

public class MoveCronTask implements Runnable {

    private final LocalDate today = LocalDate.now();
    public ScheduledTaskConfig task;
    private static final long dayInSeconds= 8640;

    public MoveCronTask(ScheduledTaskConfig task) {
        this.task = task;
    }

    @Override
    public void run() {
        if ((!getDay(today, ChestoBot.localeBr).equals("sábado")) &&
                (!getDay(today, ChestoBot.localeBr).equals("domingo"))) {
            move(RunTaskService.getGuild());

            // test purposes
            System.out.println(task.getName() + "done at " + task.getTaskTime());
        }
    }

    public void move(Guild guild){

        VoiceChannel sourceChannel = guild.getVoiceChannelById(task.getVoice().getSourceID());
        VoiceChannel targetChannel = guild.getVoiceChannelById(task.getVoice().getTargetID());

        int sourceChannelJoinedMembers = sourceChannel != null ? sourceChannel.getMembers().size() : 0;

        if (sourceChannelJoinedMembers > 0) {
            sourceChannel.getMembers().stream().filter(
                    m -> !mapExceptionMember.containsKey(m.getUser().getEffectiveName())).forEach(m -> {
                m.getGuild().moveVoiceMember(m, targetChannel).queue();
            });
        }
    }

    public static String getDay(LocalDate date, Locale locale) {
        DayOfWeek day = date.getDayOfWeek();
        return day.getDisplayName(TextStyle.FULL, locale);
    }

    public static void startSchedule(ScheduledTaskConfig runTask) {

        String[] timeString = runTask.getTaskTime().split(":");
        LocalTime runTime = LocalTime.of(Integer.parseInt(timeString[0]), Integer.parseInt(timeString[1]));

        LocalDateTime dateTime = runTime.atDate(LocalDate.now());
        long period = LocalDateTime.now().until(dateTime, ChronoUnit.SECONDS);

        /*
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Callable<MoveCronTask> moveCronTaskCallable = () -> {
            return new MoveCronTask(runTask);
        };
        Future<MoveCronTask> moveCronTaskFuture = executorService.submit(moveCronTaskCallable);
        */

        // TODO: try to create task map to manipulate run/cancel specific task
        ScheduledExecutorService threadPool = Executors.newScheduledThreadPool(1);
        //MoveCronTask moveTask = new MoveCronTask(runTask);
        //tasks.put(runTask.getName(), moveTask);
        //Future<?> f = threadPool.scheduleAtFixedRate(moveTask, period, dayInSeconds, TimeUnit.SECONDS);
        ScheduledFuture<?> f = threadPool.scheduleAtFixedRate(new MoveCronTask(runTask), period, dayInSeconds, TimeUnit.SECONDS);
        tasks.put(runTask.getName(), f);
        System.out.println(tasks);
        //ExecutorService teste = (ExecutorService) Executors.

        //ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

        //futureTask = executor.scheduleAtFixedRate(new MoveCronTask(runTask), period, dayInSeconds, TimeUnit.SECONDS);
    }

    public static void stopSchedule(ScheduledTaskConfig stopTask) {

        // is cancelling the last task assigned
        //RunTaskService.getFutureTask().cancel(false);
        //RunMoveMemberTask.getExecutorRef().shutdownNow();

        // TODO: try create task map to manipulate run/cancel specific task
        //Future<?> f = ScheduledExecutorService
        System.out.println(tasks);
        ScheduledFuture<?> f = tasks.get(stopTask.getName());
        f.cancel(false);
        if (f.isDone())
            tasks.remove(stopTask.getName());
    }
}

