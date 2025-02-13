package com.nasa.serviceBot;

import com.nasa.bot.NasaBot;
import com.nasa.serviceBot.keyboard.KeyboardFactory;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MainManager {

    KeyboardFactory keyboardFactory;

    @Autowired
    public MainManager(KeyboardFactory keyboardFactory) {
        this.keyboardFactory = keyboardFactory;
    }

    public void sendTextMessage(Long chatId, String message, NasaBot nasaBot
            , InlineKeyboardMarkup... inlineKeyboardMarkup) {
        SendMessage sendMessage = new SendMessage();

        if (inlineKeyboardMarkup.length == 0) {
            sendMessage.setChatId(chatId);
            sendMessage.setText(message);

        } else {
            sendMessage.setChatId(chatId);
            sendMessage.setText(message);
            sendMessage.setReplyMarkup(inlineKeyboardMarkup[0]);
        }
        try {
            nasaBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    public void sendCallbackQuery(SendMessage message, NasaBot nasaBot) {
        try {
            nasaBot.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
        }

    }


    public void sendVideo(Long chatId, String videoUrl, String description, NasaBot nasaBot) {

        if (videoUrl.contains("youtube.com")) {
            var index = videoUrl.lastIndexOf("/embed/") + "/embed/".length();
            var previewUrl = "https://img.youtube.com/vi/" + videoUrl.substring(index) + "/hqdefault.jpg";
            SendPhoto sendPreviewPhoto = SendPhoto.builder()
                    .chatId(chatId)
                    .photo(new InputFile(previewUrl))
                    .caption(description)
                    .replyMarkup(InlineKeyboardMarkup.builder()
                            .keyboardRow(List.of(InlineKeyboardButton.builder()
                                    .text("Переглянути відео")
                                    .url(videoUrl)
                                    .build()))
                            .build())
                    .build();
            try {
                nasaBot.execute(sendPreviewPhoto);
            } catch (TelegramApiException e) {
                e.printStackTrace();
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
                e.printStackTrace();
            }
        }

    }

    public void sendMainMenuButton(Long chatId, InlineKeyboardMarkup keyboard, NasaBot nasaBot) {
        var sendMessage = SendMessage.builder()
                .chatId(chatId)
                .replyMarkup(keyboard)
                .text("\u200B")
                .build();
        try {
            nasaBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


}
