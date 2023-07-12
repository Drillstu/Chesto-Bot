package com.bot.tasks;

import com.bot.application.ChestoBot;
import com.bot.entities.ScheduledTaskConfig;
import com.bot.services.RunMoveMemberService;
import com.bot.services.SourceVoiceService;
import com.bot.services.TargetVoiceService;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

import static com.bot.services.AddExceptionService.mapExceptionMember;

public class MoveCronTask implements Runnable {

    private final LocalDate today = LocalDate.now();
    public ScheduledTaskConfig task;
    public MoveCronTask(ScheduledTaskConfig task) {
        this.task = task;
    }

    @Override
    public void run() {
        if ((!getDay(today, ChestoBot.localeBr).equals("sábado")) &&
                (!getDay(today, ChestoBot.localeBr).equals("domingo"))) {
            move(RunMoveMemberService.getGuild());
        }
    }

    public void move(Guild guild){

        VoiceChannel sourceChannel = guild.getVoiceChannelById(SourceVoiceService.getSourceID());
        VoiceChannel targetChannel = guild.getVoiceChannelById(TargetVoiceService.getTargetID());

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
}

