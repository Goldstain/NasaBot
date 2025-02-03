package com.nasa.service;

import com.nasa.bot.NasaBot;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

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

}
