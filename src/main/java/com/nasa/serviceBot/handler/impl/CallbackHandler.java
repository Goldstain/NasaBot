package com.nasa.serviceBot.handler.impl;

import com.nasa.bot.NasaBot;
import com.nasa.config.NasaInfo;
import com.nasa.serviceBot.MainManager;
import com.nasa.serviceBot.handler.AbstractHandler;
import com.nasa.serviceBot.keyboard.KeyboardFactory;
import com.nasa.serviceNasaAPI.impl.MarsRoverPhotos;
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
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CallbackHandler implements AbstractHandler {

    MainManager manager;
    PictureOfTheDayServiceImpl pictureOfTheDayService;
    PictureOfTheDayRandomServiceImpl pictureOfTheDayRandomService;
    MarsRoverPhotos marsRoverPhotos;
    KeyboardFactory keyboardFactory;
    NasaInfo nasaInfo;


    @Autowired
    public CallbackHandler(MainManager manager
            , @Qualifier("pictureOfTheDayServiceImpl") PictureOfTheDayServiceImpl pictureOfTheDayService
            , PictureOfTheDayRandomServiceImpl pictureOfTheDayRandomService, MarsRoverPhotos marsRoverPhotos
            , KeyboardFactory keyboardFactory, NasaInfo nasaInfo) {
        this.manager = manager;
        this.pictureOfTheDayService = pictureOfTheDayService;
        this.pictureOfTheDayRandomService = pictureOfTheDayRandomService;
        this.marsRoverPhotos = marsRoverPhotos;
        this.keyboardFactory = keyboardFactory;
        this.nasaInfo = nasaInfo;
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
//            case "returnToRoversMenu":

            case "photo":
                var media = pictureOfTheDayService.constructRequest();
                sendPhotoOfTheDay(nasaBot, media, chatId);
                break;
            case "photoRandom":
                var mediaRandom = pictureOfTheDayRandomService.constructRequest();
                sendPhotoOfTheDay(nasaBot, mediaRandom, chatId);
                break;
            case "marsRoversPhotos":
                manager.sendTextMessage(chatId, nasaInfo.getRoversInfo(), nasaBot, keyboardFactory.roversMenu());
                break;
            case "opportunity":
                var roverInfo = marsRoverPhotos.constructRequest(button);
                sendRoverInfo(nasaBot, roverInfo, chatId);
                break;
            default:
                manager.sendTextMessage(chatId
                        , "Невідома кнопка", nasaBot);
                waitAndSendStartMenu(nasaBot, chatId);
        }

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

    private void sendRoverInfo(NasaBot nasaBot, Optional<List<String>> media, Long chatId) {
        String roverInfo;
        if (media.isPresent()) {
            var descriptions = media.get();
            roverInfo = descriptions.stream().collect(Collectors.joining("\n"));
        } else {
            roverInfo = "Не вдалося знайти інформацію по цьому марсоходу";
        }
        manager.sendTextMessage(chatId, roverInfo, nasaBot, keyboardFactory.returnToRoversMenu());
    }

}
