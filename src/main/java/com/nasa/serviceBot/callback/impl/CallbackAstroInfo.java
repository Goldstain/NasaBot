package com.nasa.serviceBot.callback.impl;

import com.nasa.bot.NasaBot;
import com.nasa.serviceBot.MainManager;
import com.nasa.serviceBot.callback.AbstractCallback;
import com.nasa.serviceBot.keyboard.KeyboardFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CallbackAstroInfo extends AbstractCallback {

    private final KeyboardFactory keyboardFactory;

    @Autowired
    public CallbackAstroInfo(MainManager manager, KeyboardFactory keyboardFactory) {
        super(manager, "astroInfo");
        this.keyboardFactory = keyboardFactory;
    }

    @Override
    public void execute(Long chatId, NasaBot nasaBot) {
        manager.sendTextMessage(chatId
                , "\uD83C\uDF13   Це інформація про денний цикл Сонця і Місяця в залежності від " +
                        "Вашого місцеположення "
                , nasaBot, keyboardFactory.astroInfoMenu());
    }

}
