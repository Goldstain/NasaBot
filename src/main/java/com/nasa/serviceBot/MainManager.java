package com.nasa.serviceBot;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nasa.bot.NasaBot;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MainManager {

    NasaBot nasaBot;

    @Autowired
    public MainManager(@Lazy NasaBot nasaBot) {
        this.nasaBot = nasaBot;
    }


    public void sendTextMessage(Long chatId, String message) {
        var sendMessage = SendMessage.builder()
                .chatId(chatId)
                .text(message)
                .build();
        try {
            nasaBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    public void sendCallbackQuery(SendMessage message) {
        try {
            nasaBot.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    public void sendWelcomeMessage(Long chatId) {
        var inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> keyboardRows = new ArrayList<>();

        InlineKeyboardButton mainMenuButton = new InlineKeyboardButton("🚀 Головне меню");
        mainMenuButton.setCallbackData("mainMenu");

        keyboardRows.add(List.of(mainMenuButton));

        inlineKeyboardMarkup.setKeyboard(keyboardRows);

        var sendMessage = SendMessage.builder()
                .chatId(chatId)
                .text("\t Вітаю в NASA Bot! 🌌 \n Натисніть \"Головне меню\", щоб переглянути можливості.")
                .replyMarkup(inlineKeyboardMarkup)
                .build();

        sendCallbackQuery(sendMessage);
    }

    public void sendPhoto(Long chatId, String imageUrl, String description) {
        try {

            var sendPhoto = SendPhoto.builder()
                    .chatId(chatId)
                    .photo(new InputFile(imageUrl))
                    .caption(description)
                    .build();

            nasaBot.execute(sendPhoto);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendVideo(Long chatId, String videoUrl, String description) {
        try {
            var sendVideo = SendVideo.builder()
                    .chatId(chatId)
                    .video(new InputFile(videoUrl))
                    .caption(description)
                    .build();

            System.out.println(" ####1 VIDEO "+ description+" - "+ chatId+" - "+videoUrl);
            nasaBot.execute(sendVideo);
            System.out.println(" ####2 VIDEO "+ description+" - "+ chatId+" - "+videoUrl);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


}
