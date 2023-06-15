package events;

import main.MoveBot;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

public class ReadCommand {

    public static boolean onMessageReceived(@NotNull MessageReceivedEvent event, String command) {

        String[] split = event.getMessage().getContentRaw().split(" ");
        return split[0].equalsIgnoreCase(
                MoveBot.prefixMap.get(event.getGuild().getIdLong()) + command);

    }

}
