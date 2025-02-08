package com.nasa.serviceBot.keyboard;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class KeyboardFactory {


    public InlineKeyboardMarkup mainMenuButton() {
        var inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> keyboardRows = new ArrayList<>();

        InlineKeyboardButton mainMenuButton = new InlineKeyboardButton("🚀 Головне меню");
        mainMenuButton.setCallbackData("mainMenu");

        keyboardRows.add(List.of(mainMenuButton));

        inlineKeyboardMarkup.setKeyboard(keyboardRows);
        return inlineKeyboardMarkup;
    }


    public InlineKeyboardMarkup mainMenu() {
        var inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> keyboardRows = new ArrayList<>();

        InlineKeyboardButton newButton = new InlineKeyboardButton("🚀 Останні новини NASA");
        newButton.setCallbackData("news");

        InlineKeyboardButton photoButton = new InlineKeyboardButton("\uD83C\uDF0C Фото дня");
        photoButton.setCallbackData("photo");

        List<InlineKeyboardButton> row1 = List.of(newButton, photoButton);


        InlineKeyboardButton photoRandomButton = new InlineKeyboardButton("\uD83C\uDF0C Випадкове фото дня");
        photoRandomButton.setCallbackData("photoRandom");

        List<InlineKeyboardButton> row2 = List.of(photoRandomButton);


        InlineKeyboardButton calendarButton = new InlineKeyboardButton("📅 Космічний календар");
        calendarButton.setCallbackData("calendar");

        InlineKeyboardButton helpButton = new InlineKeyboardButton("ℹ️ Допомога");
        helpButton.setCallbackData("help");

        List<InlineKeyboardButton> rows3 = List.of(calendarButton, helpButton);


        InlineKeyboardButton mainMenuButton = new InlineKeyboardButton("\uD83D\uDEF0\uFE0F Головне меню");
        mainMenuButton.setCallbackData("mainMenu");

        List<InlineKeyboardButton> rows4 = List.of(mainMenuButton);

        keyboardRows.add(row1);
        keyboardRows.add(row2);
        keyboardRows.add(rows3);
        keyboardRows.add(rows4);
        inlineKeyboardMarkup.setKeyboard(keyboardRows);
        return inlineKeyboardMarkup;
    }
}
