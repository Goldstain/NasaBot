package com.nasa.serviceBot.command.impl;

import com.nasa.bot.NasaBot;
import com.nasa.serviceBot.MainManager;
import com.nasa.serviceBot.command.AbstractCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class CommandHelp extends AbstractCommand {

    @Autowired
    public CommandHelp(MainManager manager) {
        super(manager, "/help");
    }

    @Override
    public void execute(Message message, NasaBot nasaBot) {
        manager.sendTextMessage(message.getChatId()
                , "Ось список доступних команд:\n/start - Почати\n/help - Допомога"
                , nasaBot);
    }

}
