package com.nasa.serviceBot.handler.impl;

import com.nasa.bot.NasaBot;
import com.nasa.serviceBot.MainManager;
import com.nasa.serviceBot.handler.AbstractHandler;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommandHandler implements AbstractHandler {

    MainManager manager;

    @Autowired
    public CommandHandler(MainManager manager) {
        this.manager = manager;
    }

    @Override
    public void useUpdate(Update update, NasaBot nasaBot) {
        var message = update.getMessage();
        var command = message.getText().substring(1);
        var chatId = message.getChatId();

        switch (command) {
            case "start":
                manager.sendWelcomeMessage(chatId, nasaBot);
                break;
            case "help":
                manager.sendTextMessage(chatId
                        , "Ось список доступних команд:\n/start - Почати\n/help - Допомога", nasaBot);
                break;
            default:
                manager.sendTextMessage(chatId
                        , "Невідома команда. Напиши /help ", nasaBot);
        }
    }
}
