package services;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class AddExceptionMember {
    public static final Map<String, String> mapMember = new HashMap<>();

    public void receiveCommand(@NotNull MessageReceivedEvent event) {

        TextChannel textChannel = event.getChannel().asTextChannel();

        String[] inputMember = event.getMessage().getContentRaw().split(" ",2);
        if (inputMember.length > 1) {
            mapMember.put(inputMember[1], inputMember[1]);
            textChannel.sendMessage((inputMember[1]) + " added to exception list!").queue();
        } else {
            textChannel.sendMessage("Input a member name to exception list!").queue();
        }
    }
}
