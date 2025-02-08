package com.nasa.serviceBot.handler.impl;

import com.nasa.bot.NasaBot;
import com.nasa.serviceBot.MainManager;
import com.nasa.serviceBot.handler.AbstractHandler;
import com.nasa.serviceBot.keyboard.KeyboardFactory;
import com.nasa.serviceNasaAPI.impl.PictureOfTheDayRandomServiceImpl;
import com.nasa.serviceNasaAPI.impl.PictureOfTheDayServiceImpl;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CallbackHandler implements AbstractHandler {

    MainManager manager;
    PictureOfTheDayServiceImpl pictureOfTheDayService;
    PictureOfTheDayRandomServiceImpl pictureOfTheDayRandomService;
    KeyboardFactory keyboardFactory;

    @Autowired
    public CallbackHandler(MainManager manager
            , @Qualifier("pictureOfTheDayServiceImpl") PictureOfTheDayServiceImpl pictureOfTheDayService
            , PictureOfTheDayRandomServiceImpl pictureOfTheDayRandomService
            , KeyboardFactory keyboardFactory) {
        this.manager = manager;
        this.pictureOfTheDayService = pictureOfTheDayService;
        this.pictureOfTheDayRandomService = pictureOfTheDayRandomService;
        this.keyboardFactory = keyboardFactory;
    }

    @Override
    public void useUpdate(Update update, NasaBot nasaBot) {
        var callbackQuery = update.getCallbackQuery();
        var button = callbackQuery.getData();
        var chatId = callbackQuery.getMessage().getChatId();

        switch (button) {
            case "mainMenu":
                sendStartMenu(chatId, nasaBot);
                break;
            case "photo":
                var media = pictureOfTheDayService.constructRequest();
                sendMedia(nasaBot, media, chatId);
                break;
            case "photoRandom":
                var mediaRandom = pictureOfTheDayRandomService.constructRequest();
                sendMedia(nasaBot, mediaRandom, chatId);
                break;
            default:
                manager.sendTextMessage(chatId
                        , "Невідома кнопка", nasaBot);
                waitAndSendStartMenu(nasaBot, chatId);
        }

    }

    private void sendMedia(NasaBot nasaBot, Optional<List<String>> media, Long chatId) {
        var descriptions = media.get();

        if (media.isPresent() && descriptions.size() > 0) {
            if (descriptions.getFirst().equals("image")) {
                manager.sendPhoto(chatId, descriptions.getLast()
                        , "\uD83D\uDCF8  " + descriptions.get(1), nasaBot);
            } else if (descriptions.getFirst().equals("video")) {
                manager.sendVideo(chatId, descriptions.getLast()
                        , "\uD83D\uDCF8  " + descriptions.get(1), nasaBot );
            }
            manager.sendTextMessage(chatId, descriptions.get(2), nasaBot
                    , keyboardFactory.mainMenuButton());
        } else {
            manager.sendTextMessage(chatId, "Не вдалося отримати фото дня, спробуйте іншим разом", nasaBot);
        }
    }

    private void waitAndSendStartMenu(NasaBot nasaBot, Long chatId) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                sendStartMenu(chatId, nasaBot);
            }
        }, 1200);
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
