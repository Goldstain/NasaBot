package com.nasa.serviceBot;

import com.nasa.bot.NasaBot;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MainManager {

    public void sendTextMessage(Long chatId, String message, NasaBot nasaBot) {
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


    public void sendCallbackQuery(SendMessage message, NasaBot nasaBot) {
        try {
            nasaBot.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    public void sendWelcomeMessage(Long chatId, String firstName, NasaBot nasaBot) {
        var inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> keyboardRows = new ArrayList<>();

        InlineKeyboardButton mainMenuButton = new InlineKeyboardButton("🚀 Головне меню");
        mainMenuButton.setCallbackData("mainMenu");

        keyboardRows.add(List.of(mainMenuButton));

        inlineKeyboardMarkup.setKeyboard(keyboardRows);

        var sendMessage = SendMessage.builder()
                .chatId(chatId)
                .text("\t Вітаю, " + firstName + ", в NASA Bot!  \uD83E\uDE90   \n Натисніть \"Головне меню\", щоб переглянути можливості.")
                .replyMarkup(inlineKeyboardMarkup)
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


    public void sendVideo(Long chatId, String videoUrl, String description, NasaBot nasaBot) {
        var sendVideo = SendVideo.builder()
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
