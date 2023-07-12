package com.bot.services;

import com.bot.application.ChestoBot;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

public class PingService {

    public void receiveCommand(@NotNull MessageReceivedEvent event) {

        TextChannel textChannel = event.getChannel().asTextChannel();
        textChannel.sendMessage(ChestoBot.jda.getGatewayPing() + "ms").queue();

    }
}
