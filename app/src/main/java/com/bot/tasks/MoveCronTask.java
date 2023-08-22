package com.bot.tasks;

import com.bot.entities.ScheduledTaskConfig;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

import static com.bot.services.ExceptionTaskService.mapExceptionMember;

//TODO: TRY QUARTZ FRAMEWORK
public class MoveCronTask implements Job {

    private final LocalDate today = LocalDate.now();
    public ScheduledTaskConfig task;

    public MoveCronTask(ScheduledTaskConfig task) {
        this.task = task;
    }
    public MoveCronTask() {}

    @Override
    public void execute(JobExecutionContext context) {

        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        ScheduledTaskConfig param = (ScheduledTaskConfig) dataMap.get("task");

        /*
        if ((!getDay(today, ChestoBot.localeBr).equals("sábado")) &&
                (!getDay(today, ChestoBot.localeBr).equals("domingo"))) {
            move(RunTaskServiceQuartz.getGuild(), param);

            // test purposes
            System.out.println(param.getName() + " done at " + param.getTaskTime());
        }

         */

        System.out.println("TESTE QUARTZ PARAM: " + param.getName() + " count: " + LocalTime.now()
                .format(DateTimeFormatter.ofPattern("HH:mm:ss")));

        //System.out.println("TESTE QUARTZ PARAM: " + task.getName() + " count: " + LocalTime.now()
        //        .format(DateTimeFormatter.ofPattern("HH:mm:ss")));

        //System.out.println("testando QUARTZ " + LocalTime.now()
        //       .format(DateTimeFormatter.ofPattern("HH:mm:ss")));
    }

    public void move(Guild guild, ScheduledTaskConfig param){

        VoiceChannel sourceChannel = guild.getVoiceChannelById(param.getVoice().getSourceID());
        VoiceChannel targetChannel = guild.getVoiceChannelById(param.getVoice().getTargetID());

        int sourceChannelJoinedMembers = sourceChannel != null ? sourceChannel.getMembers().size() : 0;

        if (sourceChannelJoinedMembers > 0) {
            sourceChannel.getMembers().stream().filter(
                    m -> !mapExceptionMember.containsKey(m.getUser().getEffectiveName())).forEach(m -> {
                m.getGuild().moveVoiceMember(m, targetChannel).queue();
            });
        }
    }

    public String getDay(LocalDate date, Locale locale) {
        DayOfWeek day = date.getDayOfWeek();
        return day.getDisplayName(TextStyle.FULL, locale);
    }
}