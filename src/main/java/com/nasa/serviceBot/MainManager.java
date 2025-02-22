package com.nasa.serviceBot;

import com.nasa.bot.NasaBot;
import com.nasa.serviceBot.keyboard.KeyboardFactory;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MainManager {

    Logger logger = LoggerFactory.getLogger(MainManager.class);

    KeyboardFactory keyboardFactory;

    @Autowired
    public MainManager(KeyboardFactory keyboardFactory) {
        this.keyboardFactory = keyboardFactory;
    }

    public void sendTextMessage(Long chatId, String message, NasaBot nasaBot
            , ReplyKeyboard... keyboards) {
        SendMessage sendMessage = new SendMessage();

        if (keyboards.length == 0) {
            sendMessage.setChatId(chatId);
            sendMessage.setText(message);

        } else {
            sendMessage.setChatId(chatId);
            sendMessage.setText(message);
            sendMessage.setReplyMarkup(keyboards[0]);
        }
        try {
            nasaBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            logger.error(e.getMessage());
        }
    }


    public void sendCallbackQuery(SendMessage message, NasaBot nasaBot) {
        try {
            nasaBot.execute(message);
        } catch (TelegramApiException e) {
            logger.error(e.getMessage());
        }
    }


    public void sendWelcomeMessage(Long chatId, String firstName, NasaBot nasaBot) {

        var sendMessage = SendMessage.builder()
                .chatId(chatId)
                .text("\t Вітаю, " + firstName + ", в NASA Bot!  \uD83E\uDE90   \n Натисніть \"Головне меню\", щоб переглянути можливості.")
                .replyMarkup(keyboardFactory.mainMenuButton())
                .build();

        sendCallbackQuery(sendMessage, nasaBot);
    }

    public void sendPhoto(Long chatId, String imageUrl, String description, NasaBot nasaBot) {

        SendPhoto sendPhoto = SendPhoto.builder()
                .chatId(chatId)
                .photo(new InputFile(imageUrl))
                .caption(description)
                .build();
        try {
            nasaBot.execute(sendPhoto);
        } catch (TelegramApiException e) {
            logger.error(e.getMessage());
        }

    }


    public void sendLocalPhoto(Long chatId, File file, String description, NasaBot nasaBot) {

        SendPhoto sendPhoto = SendPhoto.builder()
                .chatId(chatId)
                .photo(new InputFile(file))
                .caption(description)
                .build();
        try {
            nasaBot.execute(sendPhoto);
        } catch (TelegramApiException e) {
            logger.error(e.getMessage());
        }

    }


    public void sendVideo(Long chatId, String videoUrl, String description, NasaBot nasaBot) {

        if (videoUrl.contains("youtube.com")) {
            var index = videoUrl.lastIndexOf("/embed/") + "/embed/".length();
            var previewUrl = "https://img.youtube.com/vi/"
                    + videoUrl.substring(index, videoUrl.indexOf('?'))
                    + "/hqdefault.jpg";
            SendPhoto sendPreviewPhoto = SendPhoto.builder()
                    .chatId(chatId)
                    .photo(new InputFile(previewUrl))
                    .caption(description)
                    .replyMarkup(InlineKeyboardMarkup.builder()
                            .keyboardRow(List.of(InlineKeyboardButton.builder()
                                    .text("Переглянути на YouTube")
                                    .url(videoUrl)
                                    .build()))
                            .build())
                    .build();
            try {
                nasaBot.execute(sendPreviewPhoto);
            } catch (TelegramApiException e) {
                logger.error(e.getMessage());
            }
        } else {
            SendVideo sendVideo = SendVideo.builder()
                    .chatId(chatId)
                    .video(new InputFile(videoUrl))
                    .caption(description)
                    .build();
            try {
                nasaBot.execute(sendVideo);
            } catch (TelegramApiException e) {
                logger.error(e.getMessage());
            }
        }

    }

}
