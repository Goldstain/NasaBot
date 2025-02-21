package com.nasa.serviceBot.callback.impl;

import com.nasa.bot.NasaBot;
import com.nasa.config.NasaInfo;
import com.nasa.serviceBot.MainManager;
import com.nasa.serviceBot.callback.AbstractCallback;
import com.nasa.serviceBot.keyboard.KeyboardFactory;
import org.springframework.stereotype.Component;

@Component
public class CallbackMarsRoversPhotos extends AbstractCallback {

    private final NasaInfo nasaInfo;
    private final KeyboardFactory keyboardFactory;

    public CallbackMarsRoversPhotos(MainManager manager, NasaInfo nasaInfo, KeyboardFactory keyboardFactory) {
        super(manager, "marsRoversPhotos");
        this.nasaInfo = nasaInfo;
        this.keyboardFactory = keyboardFactory;
    }


    @Override
    public void execute(Long chatId, NasaBot nasaBot) {
        manager.sendTextMessage(chatId, nasaInfo.getRoversInfo(), nasaBot, keyboardFactory.roversMenu());
    }
}
