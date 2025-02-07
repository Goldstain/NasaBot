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
        var callbackQuery = update.getCallbackQuery();
        var button = callbackQuery.getData();
        var chatId = callbackQuery.getMessage().getChatId();

        if ("mainMenu".equals(button)) {
            sendStartMenu(chatId, nasaBot);
        } else if ("photo".equals(button)) {
            var media = pictureOfTheDayService.constructRequest();
            var descriptions = media.get();

            if (media.isPresent() && descriptions.getFirst().equals("image")) {
                manager.sendPhoto(chatId, descriptions.getLast(), "\uD83D\uDCF8  " + descriptions.get(1), nasaBot);
                manager.sendTextMessage(chatId, descriptions.get(2), nasaBot);
            } else if (media.isPresent() && descriptions.getFirst().equals("video")) {
                manager.sendVideo(chatId, descriptions.getLast(), "\uD83D\uDCF8  " + descriptions.get(1), nasaBot);
                manager.sendTextMessage(chatId, descriptions.get(2), nasaBot);

            } else {
                manager.sendTextMessage(chatId, "Не вдалося отримати фото дня, спробуйте іншим разом", nasaBot);
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

        InlineKeyboardButton mainMenuButton = new InlineKeyboardButton("\uD83D\uDEF0\uFE0F Головне меню");
        mainMenuButton.setCallbackData("mainMenu");

        List<InlineKeyboardButton> rows3 = List.of(mainMenuButton);

        keyboardRows.add(row1);
        keyboardRows.add(rows2);
        keyboardRows.add(rows3);
        inlineKeyboardMarkup.setKeyboard(keyboardRows);

        SendMessage sendMessage = SendMessage.builder()
                .chatId(chatId)
                .text("Я NASA Bot 🚀\nМаю для тебе цікаву інформацію\nОберіть команду:")
                .replyMarkup(inlineKeyboardMarkup)
                .build();

        manager.sendCallbackQuery(sendMessage, nasaBot);
    }
}
