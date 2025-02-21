package com.nasa.serviceBot.command;

import com.nasa.bot.NasaBot;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface Command {

    void execute(Message message, NasaBot nasaBot);

    String getNameCommand();
}
