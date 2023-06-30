package main.java.com.bot.services;

import main.java.com.bot.application.ChestoBot;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

public class Ping {

    public void receiveCommand(@NotNull MessageReceivedEvent event) {

        TextChannel textChannel = event.getChannel().asTextChannel();
        textChannel.sendMessage(ChestoBot.jda.getGatewayPing() + "ms").queue();

    }
}
