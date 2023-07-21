package com.bot.services;

import com.bot.database.CRUD;
import com.bot.entities.ScheduledTaskConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;

import static com.bot.tasks.MoveCronTask.startSchedule;
import static com.bot.utils.DocumentToObject.toObject;

public class RunTaskService {
    // TODO: 13/06/2023 more tests should be done with ExecutorService, such as if a task will run repeatedly every day at specific time
    private static Guild guild;
    private static Future<?> futureTask;
    private static final long dayInSeconds= 8640;
    public static Map<String, ScheduledFuture<?>> tasks = new HashMap<>();
    public static Future<?> getFutureTask() { return futureTask; }
    public static Guild getGuild() {
        return guild;
    }

    public void receiveCommand(@NotNull MessageReceivedEvent event) throws JsonProcessingException {

        TextChannel textChannel = event.getChannel().asTextChannel();

        guild = event.getGuild();

        String[] inputTaskName = event.getMessage().getContentRaw().split(" ",2);
        if (!(inputTaskName.length > 1) || (inputTaskName[1].isEmpty())) {

            textChannel.sendMessage("No task name has set!").queue();

        } else {

            Document readDocument = CRUD.read(inputTaskName[1]);
            if (readDocument != null) {

                ScheduledTaskConfig runTask = (ScheduledTaskConfig) toObject(readDocument);

                /*
                String[] timeString = runTask.getTaskTime().split(":");
                LocalTime runTime = LocalTime.of(Integer.parseInt(timeString[0]), Integer.parseInt(timeString[1]));

                LocalDateTime dateTime = runTime.atDate(LocalDate.now());
                long period = LocalDateTime.now().until(dateTime, ChronoUnit.SECONDS);
                */

                if (!runTask.getActive()) {
                    startSchedule(runTask);
                    /*
                    ExecutorService executorService = Executors.newSingleThreadExecutor();
                    Callable<MoveCronTask> moveCronTaskCallable = () -> {
                        return new MoveCronTask(runTask);
                    };
                    Future<MoveCronTask> moveCronTaskFuture = executorService.submit(moveCronTaskCallable);
                    */

                    /*
                    // TODO: try to create task map to manipulate run/cancel specific task
                    ScheduledExecutorService threadPool = Executors.newScheduledThreadPool(1);
                    //MoveCronTask moveTask = new MoveCronTask(runTask);
                    //tasks.put(runTask.getName(), moveTask);
                    //Future<?> f = threadPool.scheduleAtFixedRate(moveTask, period, dayInSeconds, TimeUnit.SECONDS);
                    ScheduledFuture<?> f = threadPool.scheduleAtFixedRate(new MoveCronTask(runTask), period, dayInSeconds, TimeUnit.SECONDS);
                    tasks.put(runTask.getName(), f);
                    //ExecutorService teste = (ExecutorService) Executors.

                    //ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

                    //futureTask = executor.scheduleAtFixedRate(new MoveCronTask(runTask), period, dayInSeconds, TimeUnit.SECONDS);
                    */
                    runTask.setActive(true);
                    CRUD.update(runTask, readDocument);
                    textChannel.sendMessage("Task " + runTask.getName() + " active!").queue();
                } else {
                    textChannel.sendMessage("Task " + runTask.getName() + " is already active!").queue();
                }
            } else {
                textChannel.sendMessage("No such task name currently exist!").queue();
            }
        }
    }
}