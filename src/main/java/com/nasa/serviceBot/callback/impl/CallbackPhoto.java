package com.nasa.serviceBot.callback.impl;

import com.nasa.bot.NasaBot;
import com.nasa.serviceBot.MainManager;
import com.nasa.serviceBot.callback.AbstractCallback;
import com.nasa.serviceBot.keyboard.KeyboardFactory;
import com.nasa.serviceNasaAPI.impl.PictureOfTheDayServiceImpl;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Getter
@Component
public class CallbackPhoto extends AbstractCallback {

    private final PictureOfTheDayServiceImpl pictureOfTheDayService;
    private final KeyboardFactory keyboardFactory;

    @Autowired
    public CallbackPhoto(MainManager manager
            , PictureOfTheDayServiceImpl pictureOfTheDayService
            , KeyboardFactory keyboardFactory) {
        super(manager, "photo");
        this.pictureOfTheDayService = pictureOfTheDayService;
        this.keyboardFactory = keyboardFactory;
    }


    @Override
    public void execute(Long chatId, NasaBot nasaBot) {
        var media = pictureOfTheDayService.constructResponse();
        sendPhotoOfTheDay(nasaBot, media, chatId);
    }

    private void sendPhotoOfTheDay(NasaBot nasaBot, Optional<List<String>> media, Long chatId) {
        var descriptions = media.get();

        if (media.isPresent() && descriptions.size() > 0) {
            if (descriptions.getFirst().equals("image")) {
                manager.sendPhoto(chatId, descriptions.getLast()
                        , "\uD83D\uDCF8  " + descriptions.get(1), nasaBot);
            } else if (descriptions.getFirst().equals("video")) {
                manager.sendVideo(chatId, descriptions.getLast()
                        , "\uD83D\uDCF8  " + descriptions.get(1), nasaBot);
            }
            manager.sendTextMessage(chatId, descriptions.get(2), nasaBot
                    , keyboardFactory.mainMenuButton());
        } else {
            manager.sendTextMessage(chatId, "Не вдалося отримати фото дня, спробуйте іншим разом", nasaBot);
        }
    }
}
