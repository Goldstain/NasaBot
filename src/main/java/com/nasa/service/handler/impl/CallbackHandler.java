package com.nasa.service.handler.impl;

import com.nasa.bot.NasaBot;
import com.nasa.service.MainManager;
import com.nasa.service.handler.AbstractHandler;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CallbackHandler extends AbstractHandler {

    MainManager manager;

    @Autowired
    public CallbackHandler(MainManager manager) {
        this.manager = manager;
    }

    @Override
    public void useUpdate(Update update) {
        var callbackQuery = update.getCallbackQuery().getData();
        var chatId = update.getCallbackQuery().getMessage().getChatId();

        if ("mainMenu".equals(callbackQuery)) {
            sendStartMenu(chatId);
        } else {
            manager.sendTextMessage(chatId
                    , "Невідома кнопка");
        }

    }



    private void sendStartMenu(Long chatId) {
        var inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> keyboardRows = new ArrayList<>();

        InlineKeyboardButton newButton = new InlineKeyboardButton("🚀 Останні новини NASA");
        newButton.setCallbackData("news");

        InlineKeyboardButton photoButton = new InlineKeyboardButton("\uD83C\uDF0C Фото дня");
        photoButton.setCallbackData("photo");

        List<InlineKeyboardButton> row1 = List.of(newButton, photoButton);


        InlineKeyboardButton calendarButton = new InlineKeyboardButton("📅 Космічний календар");
        calendarButton.setCallbackData("calendar");


        InlineKeyboardButton helpButton = new InlineKeyboardButton("ℹ️ Допомога");
        helpButton.setCallbackData("help");

        List<InlineKeyboardButton> rows2 = List.of(calendarButton, helpButton);

        keyboardRows.add(row1);
        keyboardRows.add(rows2);
        inlineKeyboardMarkup.setKeyboard(keyboardRows);

        SendMessage sendMessage = SendMessage.builder()
                .chatId(chatId)
                .text("Вітаю! Я NASA Bot 🚀\nОберіть команду:")
                .replyMarkup(inlineKeyboardMarkup)
                .build();

        manager.sendCallbackQuery(sendMessage);
    }
}
