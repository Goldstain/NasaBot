package com.nasa.serviceBot.handler.impl;

import com.nasa.bot.NasaBot;
import com.nasa.exception.CallbackNotFoundException;
import com.nasa.serviceBot.MainManager;
import com.nasa.serviceBot.callback.Callback;
import com.nasa.serviceBot.handler.Handler;
import com.nasa.serviceBot.keyboard.KeyboardFactory;
import com.nasa.serviceNasaAPI.dto.Photos;
import com.nasa.serviceNasaAPI.dto.PhotosByDateCamera;
import com.nasa.serviceNasaAPI.impl.MarsRoverPhotos;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CallbackHandler implements Handler {

    Logger logger = LoggerFactory.getLogger(CallbackHandler.class);

    List<Callback> commands;
    MainManager manager;
    MarsRoverPhotos marsRoverPhotos;
    KeyboardFactory keyboardFactory;
    public static String[] availableDataPhotos = new String[2];


    @Autowired
    public CallbackHandler(List<Callback> commands
            , MainManager manager, MarsRoverPhotos marsRoverPhotos, KeyboardFactory keyboardFactory) {
        this.commands = commands;
        this.manager = manager;
        this.marsRoverPhotos = marsRoverPhotos;
        this.keyboardFactory = keyboardFactory;
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
        Callback callback;
        try {
            callback = commands.stream()
                    .filter(command -> command.getNameCallback().equals(button))
                    .findFirst()
                    .orElseThrow(() -> new CallbackNotFoundException());
            callback.execute(chatId, nasaBot);
        } catch (CallbackNotFoundException e) {
            logger.error(update.getCallbackQuery().getData()
                    + " - button not found");
            manager.sendTextMessage(chatId, "Невідома кнопка", nasaBot);
            waitAndSendStartMenu(nasaBot, chatId);
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
                chatId, "\uD83D\uDCF9   Вибрати іншу камеру     \uD83D\uDD3D"
                , nasaBot, keyboardFactory.availableCamerasKeyboard(earthDate));
    }

}
