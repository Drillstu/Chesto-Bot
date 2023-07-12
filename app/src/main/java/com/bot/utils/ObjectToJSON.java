package com.bot.utils;

import com.bot.entities.ScheduledTaskConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectToJSON {

    public String convertToJSON(ScheduledTaskConfig task) throws JsonProcessingException {

        // mexer aqui: definir local de set de prop. de Task
        // futuro: se houver + de 1 Task, aqui receberia somente o obj?
        //Task task = new Task();
        //task.setServerID(RunMoveMemberTask.getGuild().getName());
        //task.setSourceName(SourceVoiceChannel.getSourceName());
        //task.setTargetName(TargetVoiceChannel.getTargetName());
        //task.setTaskTime(RunMoveMemberTask.dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        //task.setExceptMember(mapExceptionMember);



        ObjectMapper mapper = new ObjectMapper();

        return mapper.writeValueAsString(task);
    }
}
