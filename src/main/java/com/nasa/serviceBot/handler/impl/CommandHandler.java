package com.nasa.serviceBot.handler.impl;

import com.nasa.bot.NasaBot;
import com.nasa.serviceBot.MainManager;
import com.nasa.serviceBot.handler.AbstractHandler;
import com.nasa.serviceBot.keyboard.KeyboardFactory;
import com.nasa.serviceNasaAPI.impl.AstroInfo;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommandHandler implements AbstractHandler {

    MainManager manager;
    KeyboardFactory keyboardFactory;
    AstroInfo astroInfo;
    public static String currentDate = "";

    @Autowired
    public CommandHandler(MainManager manager, KeyboardFactory keyboardFactory, AstroInfo astroInfo) {
        this.manager = manager;
        this.keyboardFactory = keyboardFactory;
        this.astroInfo = astroInfo;
    }

    @Override
    public void useUpdate(Update update, NasaBot nasaBot) {
        var message = update.getMessage();
        var command = message.getText();
        var chatId = message.getChatId();

        if (message.hasLocation()) {
            Location location = message.getLocation();
            Optional<String> astroInfoResult
                    = astroInfo.constructResponse(location.getLatitude().toString()
                    , location.getLongitude().toString());

            var result = astroInfoResult.isPresent()
                    ? astroInfoResult.get()
                    : "Невдалося отримати інформацію, спробуйте пізніше";

            manager.sendTextMessage(chatId, astroInfoResult.get(), nasaBot, keyboardFactory.mainMenuButton());
            return;
        }


        switch (command) {
            case "/start":
                var firstName = message.getFrom().getFirstName();
                manager.sendWelcomeMessage(chatId, firstName, nasaBot);
                break;
            case "/help":
                manager.sendTextMessage(chatId
                        , "Ось список доступних команд:\n/start - Почати\n/help - Допомога", nasaBot);
                break;
            default:
                if (isCorrectDate(command).isPresent()) {
                    var keyboard = keyboardFactory.availableCamerasKeyboard(command);
                    var description = keyboard.getKeyboard().size() == 1 ?
                            command.concat("  немає доступних камер") :
                            "Це список камер марсоходу доступних " + command + ", обери камеру";
                    currentDate = command;
                    manager.sendTextMessage(chatId, description
                            , nasaBot, keyboard);
                } else {
                    manager.sendTextMessage(chatId
                            , "Невідома команда. Напиши /help або повернись до меню ", nasaBot);
                }
        }
    }

    private Optional<String> isCorrectDate(String command) {
        String regex = "20[0-3][0-9]-(0[0-9]|1[0-2])-([0-2][0-9]|3[0-1])";
        boolean matches = Pattern.matches(regex, command);
        if (!matches) return Optional.empty();

        String start = CallbackHandler.availableDataPhotos[0];
        String end = CallbackHandler.availableDataPhotos[1];
        if (start == null || end == null ||
                !Pattern.matches(regex, start) || !Pattern.matches(regex, end))
            return Optional.empty();

        LocalDate date = null;
        try {
            date = LocalDate.parse(command);
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            return Optional.empty();
        }
        LocalDate startDate = LocalDate.parse(CallbackHandler.availableDataPhotos[0]);
        LocalDate endDate = LocalDate.parse(CallbackHandler.availableDataPhotos[1]);

        if (date.isAfter(startDate.minusDays(1)) && date.isBefore(endDate.plusDays(1))) {
            return Optional.of(date.toString());
        }
        return Optional.empty();
    }
}
