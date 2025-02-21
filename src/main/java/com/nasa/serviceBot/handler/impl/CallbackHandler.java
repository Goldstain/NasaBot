package com.nasa.serviceBot.handler.impl;

import com.nasa.bot.NasaBot;
import com.nasa.config.NasaConfig;
import com.nasa.config.NasaInfo;
import com.nasa.serviceBot.MainManager;
import com.nasa.serviceBot.handler.Handler;
import com.nasa.serviceBot.keyboard.KeyboardFactory;
import com.nasa.serviceNasaAPI.dto.Photos;
import com.nasa.serviceNasaAPI.dto.PhotosByDateCamera;
import com.nasa.serviceNasaAPI.impl.MarsRoverPhotos;
import com.nasa.serviceNasaAPI.impl.NewsRSS;
import com.nasa.serviceNasaAPI.impl.PictureOfTheDayRandomServiceImpl;
import com.nasa.serviceNasaAPI.impl.PictureOfTheDayServiceImpl;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.List;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CallbackHandler implements Handler {

    MainManager manager;
    PictureOfTheDayServiceImpl pictureOfTheDayService;
    PictureOfTheDayRandomServiceImpl pictureOfTheDayRandomService;
    MarsRoverPhotos marsRoverPhotos;
    KeyboardFactory keyboardFactory;
    NasaInfo nasaInfo;
    NasaConfig nasaConfig;
    NewsRSS newsRSS;
    static String[] availableDataPhotos = new String[2];


    @Autowired
    public CallbackHandler(MainManager manager
            , @Qualifier("pictureOfTheDayServiceImpl") PictureOfTheDayServiceImpl pictureOfTheDayService
            , PictureOfTheDayRandomServiceImpl pictureOfTheDayRandomService, MarsRoverPhotos marsRoverPhotos
            , KeyboardFactory keyboardFactory, NasaInfo nasaInfo, NasaConfig nasaConfig, NewsRSS newsRSS) {
        this.manager = manager;
        this.pictureOfTheDayService = pictureOfTheDayService;
        this.pictureOfTheDayRandomService = pictureOfTheDayRandomService;
        this.marsRoverPhotos = marsRoverPhotos;
        this.keyboardFactory = keyboardFactory;
        this.nasaInfo = nasaInfo;
        this.nasaConfig = nasaConfig;
        this.newsRSS = newsRSS;
    }

    @Override
    public void useUpdate(Update update, NasaBot nasaBot) {
        var callbackQuery = update.getCallbackQuery();
        var button = callbackQuery.getData();
        var chatId = callbackQuery.getMessage().getChatId();

        if (button.endsWith(":roverCamera")) {
            sendCameraPhotos(button, chatId, nasaBot);
            return;
        }

        switch (button) {
            case "help":
                manager.sendTextMessage(chatId
                        , "Ось список доступних команд:\n/start - Почати\n/help - Допомога", nasaBot);
                break;
            case "mainMenu":
                sendStartMenu(chatId, nasaBot);
                break;
            case "news":
                sendLatestNews(chatId, nasaBot);
                break;
            case "photo":
                var media = pictureOfTheDayService.constructResponse();
                sendPhotoOfTheDay(nasaBot, media, chatId);
                break;
            case "photoRandom":
                var mediaRandom = pictureOfTheDayRandomService.constructResponse();
                sendPhotoOfTheDay(nasaBot, mediaRandom, chatId);
                break;
            case "marsRoversPhotos":
                manager.sendTextMessage(chatId, nasaInfo.getRoversInfo(), nasaBot, keyboardFactory.roversMenu());
                break;
            case "spirit":
            case "opportunity":
            case "curiosity":
                var roverInfo = marsRoverPhotos.constructResponse(button);
                sendRoverInfo(nasaBot, roverInfo, chatId);
                break;
            case "curiosityPhotos":
                var description = "Введіть доступну дату в діапазоні від \n" + availableDataPhotos[0] + " до "
                        + availableDataPhotos[1] + "\nВ форматі РРРР-ММ-ДД\nНаприклад: 2022-08-24";
                manager.sendTextMessage(chatId, description, nasaBot, keyboardFactory.returnToRoversMenu());
                break;
            case "astroInfo":
                manager.sendTextMessage(chatId, "\uD83C\uDF13   Це інформація про денний цикл Сонця і Місяця в залежності від " +
                        "Вашого місцеположення ", nasaBot, keyboardFactory.astroInfoMenu());
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

    private void sendRoverInfo(NasaBot nasaBot, Optional<String> roverInfoOpt, Long chatId) {
        String roverInfo;
        InlineKeyboardMarkup keyboardMarkup = keyboardFactory.returnToRoversMenu();
        if (roverInfoOpt.isPresent()) {
            roverInfo = roverInfoOpt.get();
        } else {
            roverInfo = "Не вдалося знайти інформацію по цьому марсоходу";
        }

        if (roverInfo.contains("Curiosity")) {
            keyboardMarkup = keyboardFactory.roverPhotoButton(keyboardMarkup);
        }
        setAvailableDataPhotos(roverInfo);

        manager.sendTextMessage(chatId, roverInfo, nasaBot, keyboardMarkup);
    }


    private void setAvailableDataPhotos(String roverInfo) {
        var start = "Прибув на Марс: ";
        var end = "Остання дата отриманих знімків: ";

        int startDate = roverInfo.indexOf(start) + start.length();
        int endDate = roverInfo.indexOf(end) + end.length();

        availableDataPhotos[0] = roverInfo.substring(startDate, startDate + 10);
        availableDataPhotos[1] = roverInfo.substring(endDate, endDate + 10);
    }


    private void sendCameraPhotos(String button, Long chatId, NasaBot nasaBot) {
        var camera = button.substring(0, button.lastIndexOf(":roverCamera"));
        Photos photos = marsRoverPhotos.getPhotos(camera);
        if (photos == null || photos.getPhotos().length == 0) return;

        PhotosByDateCamera[] photosArray = photos.getPhotos();
        String earthDate = photosArray[0].getEarth_date();
        int count = 10;
        for (PhotosByDateCamera photo : photosArray) {
            manager.sendPhoto(chatId
                    , photo.getImg_src()
                    , "Фото ID: " + photo.getId() + "\t\t\t" + photo.getEarth_date()
                    , nasaBot);
            count--;
            if (count == 0) break;
        }
        manager.sendTextMessage(
                chatId, "\uD83D\uDCF9   Вибрати іншу камеру     \uD83D\uDD3D", nasaBot, keyboardFactory.availableCamerasKeyboard(earthDate));
    }

    private void sendLatestNews(Long chatId, NasaBot nasaBot) {
        List<String> news = newsRSS.constructResponse().orElseGet(() -> List.of("Новин немає"));

        StringBuilder newsMessage = new StringBuilder("Останні новини NASA:\n\n");
        for (String item : news) {
            newsMessage.append(item).append("\n\n");
        }

        manager.sendTextMessage(chatId, newsMessage.toString(), nasaBot, keyboardFactory.mainMenuButton());
    }


}
