package com.nasa.service.handler.impl;

import com.nasa.service.MainManager;
import com.nasa.service.handler.AbstractHandler;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommandHandler extends AbstractHandler {

    MainManager manager;

    @Autowired
    public CommandHandler(MainManager manager) {
        this.manager = manager;
    }

    @Override
    public void useUpdate(Update update) {
        var message = update.getMessage();
        var command = message.getText().substring(1);
        var chatId = message.getChatId();

        switch (command) {
            case "start":
                manager.sendTextMessage(chatId
                        , "Привіт! Я NASA бот. Напиши /help для списку команд.");
                break;
            case "help":
                manager.sendTextMessage(chatId
                        , "Ось список доступних команд:\n/start - Почати\n/help - Допомога");
                break;
            default:
                manager.sendTextMessage(chatId
                        , "Невідома команда. Напиши /help ");
        }
    }
}
