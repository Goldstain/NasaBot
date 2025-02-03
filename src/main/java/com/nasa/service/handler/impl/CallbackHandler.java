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
public class CallbackHandler extends AbstractHandler {

    MainManager manager;

    @Autowired
    public CallbackHandler(MainManager manager) {
        this.manager = manager;
    }

    @Override
    public void useUpdate(Update update) {
        var callbackQuery = update.getCallbackQuery().getData();
        var chatId = update.getMessage().getChatId();

        if ("INFO".equals(callbackQuery)) {
            manager.sendTextMessage(chatId
                    , "Це NASA бот. Він допомагає отримати інформацію про космос.");
        } else {
            manager.sendTextMessage(chatId
                    , "Невідома кнопка");
        }

    }
}
