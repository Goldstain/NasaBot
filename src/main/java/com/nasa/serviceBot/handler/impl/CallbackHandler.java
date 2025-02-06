package com.nasa.serviceBot.handler.impl;

import com.nasa.bot.NasaBot;
import com.nasa.serviceBot.MainManager;
import com.nasa.serviceBot.handler.AbstractHandler;
import com.nasa.serviceNasaAPI.impl.PictureOfTheDayServiceImpl;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CallbackHandler implements AbstractHandler {

    MainManager manager;
    PictureOfTheDayServiceImpl pictureOfTheDayService;

    @Autowired
    public CallbackHandler(MainManager manager, PictureOfTheDayServiceImpl pictureOfTheDayService) {
        this.manager = manager;
        this.pictureOfTheDayService = pictureOfTheDayService;
    }

    @Override
    public void useUpdate(Update update, NasaBot nasaBot) {
        var callbackQuery = update.getCallbackQuery().getData();
        var chatId = update.getCallbackQuery().getMessage().getChatId();

        if ("mainMenu".equals(callbackQuery)) {
            sendStartMenu(chatId, nasaBot);
        } else if ("photo".equals(callbackQuery)) {
            var media = pictureOfTheDayService.constructRequest();

            if (media.isPresent() && media.get().getFirst().equals("image")) {
                manager.sendPhoto(chatId, media.get().getLast(), "\uD83D\uDCF8 Astronomy Picture of the Day",nasaBot);
            } else if (media.isPresent() && media.get().getFirst().equals("video")) {
                manager.sendVideo(chatId, media.get().getLast(), "\uD83D\uDCF8 Astronomy Video of the Day",nasaBot);
            } else {
                manager.sendTextMessage(chatId, "Не вдалося отримати фото дня, спробуйте іншим разом",nasaBot);
            }
        } else {
            manager.sendTextMessage(chatId
                    , "Невідома кнопка", nasaBot);
        }

    }


    private void sendStartMenu(Long chatId, NasaBot nasaBot) {
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

        manager.sendCallbackQuery(sendMessage, nasaBot);
    }
}
