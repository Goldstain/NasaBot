package com.nasa.serviceBot.callback.impl;

import com.nasa.bot.NasaBot;
import com.nasa.serviceBot.MainManager;
import com.nasa.serviceBot.callback.AbstractCallback;
import com.nasa.serviceBot.keyboard.KeyboardFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.nasa.serviceBot.handler.impl.CallbackHandler.availableDataPhotos;

@Component
public class CallbackCuriosityPhotos extends AbstractCallback {


    private final KeyboardFactory keyboardFactory;

    @Autowired
    public CallbackCuriosityPhotos(MainManager manager, KeyboardFactory keyboardFactory) {
        super(manager, "curiosityPhotos");
        this.keyboardFactory = keyboardFactory;
    }


    @Override
    public void execute(Long chatId, NasaBot nasaBot) {
        var description = "Введіть доступну дату в діапазоні від \n" + availableDataPhotos[0] + " до "
                + availableDataPhotos[1] + "\nВ форматі РРРР-ММ-ДД\nНаприклад: 2022-08-24";
        manager.sendTextMessage(chatId, description, nasaBot, keyboardFactory.returnToRoversMenu());
    }
}
