package com.nasa.serviceBot.command.impl;

import com.nasa.bot.NasaBot;
import com.nasa.serviceBot.MainManager;
import com.nasa.serviceBot.command.AbstractCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class CommandStart extends AbstractCommand {

    @Autowired
    public CommandStart(MainManager manager) {
        super(manager, "/start");
    }

    @Override
    public void execute(Message message, NasaBot nasaBot) {
        var firstName = message.getFrom().getFirstName();
        manager.sendWelcomeMessage(message.getChatId(), firstName, nasaBot);
    }
}
