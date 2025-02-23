package com.nasa.serviceBot.handler.impl;

import com.nasa.bot.NasaBot;
import com.nasa.exception.CommandNotFoundException;
import com.nasa.serviceBot.MainManager;
import com.nasa.serviceBot.command.Command;
import com.nasa.serviceBot.handler.Handler;
import com.nasa.serviceBot.keyboard.KeyboardFactory;
import com.nasa.serviceNasaAPI.impl.AstroInfo;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommandHandler implements Handler {

    Logger logger = LoggerFactory.getLogger(CommandHandler.class);

    List<Command> commands;
    MainManager manager;
    KeyboardFactory keyboardFactory;
    AstroInfo astroInfo;
    public static String currentDate = "";

    @Autowired
    public CommandHandler(List<Command> commands, MainManager manager
            , KeyboardFactory keyboardFactory, AstroInfo astroInfo) {
        this.commands = commands;
        this.manager = manager;
        this.keyboardFactory = keyboardFactory;
        this.astroInfo = astroInfo;
    }

    @Override
    public void useUpdate(Update update, NasaBot nasaBot) {
        var message = update.getMessage();
        var textCommand = message.getText();
        var chatId = message.getChatId();

        if (message.hasLocation()) {
            sendAstroInfoByIP(nasaBot, message, chatId);
            return;
        } else if (isCorrectCoordinates(textCommand)) {
            sendAstroInfoByCoordinates(chatId, textCommand, nasaBot);
            return;
        } else if (isCorrectDate(textCommand).isPresent()) {
            var keyboard = keyboardFactory.availableCamerasKeyboard(textCommand);
            var description = keyboard.getKeyboard().size() == 1 ?
                    textCommand.concat("  немає доступних камер") :
                    "Це список камер марсоходу доступних " + textCommand + ", обери камеру";
            currentDate = textCommand;
            manager.sendTextMessage(chatId, description
                    , nasaBot, keyboard);
            return;
        }

        Command command;
        try {
            command = commands.stream()
                    .filter(com -> com.getNameCommand().equals(textCommand))
                    .findFirst()
                    .orElseThrow(() -> new CommandNotFoundException());
            command.execute(message, nasaBot);
        } catch (CommandNotFoundException e) {
            logger.info("User: " + message.getFrom().getFirstName() + " wrote wrong command:  " + textCommand);
            manager.sendTextMessage(chatId
                    , "Невідома команда. Напиши /help або повернись до меню ", nasaBot);
        }
    }

    private void sendAstroInfoByIP(NasaBot nasaBot, Message message, Long chatId) {
        Location location = message.getLocation();
        Optional<String> astroInfoResult
                = astroInfo.constructResponse(location.getLatitude().toString()
                , location.getLongitude().toString());

        var result = astroInfoResult.isPresent()
                ? astroInfoResult.get()
                : "Невдалося отримати інформацію, спробуйте пізніше";

        manager.sendTextMessage(chatId, astroInfoResult.get(), nasaBot, keyboardFactory.mainMenuButton());
    }


    private boolean isCorrectCoordinates(String coordinates) {
        String regex = "^-?(90(\\.0+)?|[0-8]?[0-9](\\.[0-9]+)?)\\s+-?(180(\\.0+)?|1[0-7][0-9](\\.[0-9]+)?|[0-9]?[0-9](\\.[0-9]+)?)$";
        return Pattern.matches(regex, coordinates);
    }

    private void sendAstroInfoByCoordinates(Long chatId, String coordinates, NasaBot nasaBot) {
        String latitude = coordinates.substring(0, coordinates.indexOf(" "));
        String longitude = coordinates.substring(coordinates.lastIndexOf(" ")).trim();

        Optional<String> astroInfoResult = astroInfo.constructResponse(latitude, longitude);
        manager.sendTextMessage(chatId, astroInfoResult.get(), nasaBot, keyboardFactory.mainMenuButton());
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
            logger.warn(e.getMessage() + " Wrong LocalDate.parse(" + command + ")");
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
