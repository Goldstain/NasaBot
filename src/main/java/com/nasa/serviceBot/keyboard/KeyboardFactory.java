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

    public InlineKeyboardMarkup returnBackButton() {
        var inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboardRows = new ArrayList<>();

        var inlineKeyboardButton = new InlineKeyboardButton("Назад");
        inlineKeyboardButton.setCallbackData("returnBack");

        keyboardRows.add(List.of(inlineKeyboardButton));
        inlineKeyboardMarkup.setKeyboard(keyboardRows);
        return inlineKeyboardMarkup;
    }


    public InlineKeyboardMarkup mainMenu() {
        var inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> keyboardRows = new ArrayList<>();

        InlineKeyboardButton newButton = new InlineKeyboardButton("\uD83D\uDEF0\uFE0F Останні новини NASA");
        newButton.setCallbackData("news");

        InlineKeyboardButton photoButton = new InlineKeyboardButton("\uD83C\uDF0C Фото дня");
        photoButton.setCallbackData("photo");

        List<InlineKeyboardButton> row1 = List.of(newButton, photoButton);


        InlineKeyboardButton photoRandomButton = new InlineKeyboardButton("\uD83C\uDF0C Випадкове фото дня");
        photoRandomButton.setCallbackData("photoRandom");

        List<InlineKeyboardButton> row2 = List.of(photoRandomButton);


        InlineKeyboardButton marsRoversPhotosButton = new InlineKeyboardButton("\uD83D\uDCF8 \uD83D\uDFE0  Фото з марсоходів");
        marsRoversPhotosButton.setCallbackData("marsRoversPhotos");

        List<InlineKeyboardButton> rows3 = List.of(marsRoversPhotosButton);


        InlineKeyboardButton calendarButton = new InlineKeyboardButton("📅 Космічний календар");
        calendarButton.setCallbackData("calendar");

        InlineKeyboardButton helpButton = new InlineKeyboardButton("ℹ️ Допомога");
        helpButton.setCallbackData("help");

        List<InlineKeyboardButton> rows4 = List.of(calendarButton, helpButton);

        keyboardRows.add(row1);
        keyboardRows.add(row2);
        keyboardRows.add(rows3);
        keyboardRows.add(rows4);
        inlineKeyboardMarkup.setKeyboard(keyboardRows);
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup roversMenu() {
        var inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboardRows = new ArrayList<>();

        InlineKeyboardButton button1 = new InlineKeyboardButton("\uD83D\uDCFD\uFE0F Curiosity");
        button1.setCallbackData("curiosity");
        List<InlineKeyboardButton> row1 = List.of(button1);

        InlineKeyboardButton button2 = new InlineKeyboardButton("\uD83C\uDFA5 Opportunity");
        button2.setCallbackData("opportunity");
        List<InlineKeyboardButton> row2 = List.of(button2);

        InlineKeyboardButton button3 = new InlineKeyboardButton("\uD83C\uDFA5 Spirit");
        button3.setCallbackData("spirit");
        List<InlineKeyboardButton> row3 = List.of(button3);

        keyboardRows.add(row1);
        keyboardRows.add(row2);
        keyboardRows.add(row3);
        keyboardRows.add(mainMenuButton().getKeyboard().getFirst());
        inlineKeyboardMarkup.setKeyboard(keyboardRows);

        return inlineKeyboardMarkup;
    }
}
