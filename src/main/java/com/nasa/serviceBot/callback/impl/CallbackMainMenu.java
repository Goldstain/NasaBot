package com.nasa.serviceBot.callback.impl;

import com.nasa.bot.NasaBot;
import com.nasa.serviceBot.MainManager;
import com.nasa.serviceBot.callback.AbstractCallback;
import com.nasa.serviceBot.keyboard.KeyboardFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Component
public class CallbackMainMenu extends AbstractCallback {

    private final KeyboardFactory keyboardFactory;

    @Autowired
    public CallbackMainMenu(MainManager manager, KeyboardFactory keyboardFactory) {
        super(manager, "mainMenu");
        this.keyboardFactory = keyboardFactory;
    }


    @Override
    public void execute(Long chatId, NasaBot nasaBot) {
        sendStartMenu(chatId, nasaBot);
    }

    private void sendStartMenu(Long chatId, NasaBot nasaBot) {
        SendMessage sendMessage = SendMessage.builder()
                .chatId(chatId)
                .text("Я NASA Bot 🚀\nМаю для тебе цікаву інформацію\nОберіть команду:")
                .replyMarkup(keyboardFactory.mainMenu())
                .build();

        manager.sendCallbackQuery(sendMessage, nasaBot);
    }
}
