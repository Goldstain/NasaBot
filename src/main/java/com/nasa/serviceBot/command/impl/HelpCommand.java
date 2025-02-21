package com.nasa.serviceBot.command.impl;

import com.nasa.bot.NasaBot;
import com.nasa.serviceBot.MainManager;
import com.nasa.serviceBot.command.AbstractCallbackCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HelpCommand extends AbstractCallbackCommand {

    @Autowired
    public HelpCommand(MainManager manager) {
        super(manager, "help");
    }


    @Override
    public void execute(Long chatId, NasaBot nasaBot) {
        manager.sendTextMessage(chatId
                , "Ось список доступних команд:\n/start - Почати\n/help - Допомога"
                , nasaBot);
    }

}
