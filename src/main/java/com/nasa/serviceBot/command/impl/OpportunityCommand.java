package com.nasa.serviceBot.command.impl;

import com.nasa.bot.NasaBot;
import com.nasa.serviceBot.MainManager;
import com.nasa.serviceBot.command.AbstractCallbackCommand;
import com.nasa.serviceBot.keyboard.KeyboardFactory;
import com.nasa.serviceNasaAPI.impl.MarsRoverPhotos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.Optional;

import static com.nasa.serviceBot.handler.impl.CallbackHandler.availableDataPhotos;

@Component
public class OpportunityCommand extends AbstractCallbackCommand {

    private final MarsRoverPhotos marsRoverPhotos;
    private final KeyboardFactory keyboardFactory;

    @Autowired
    public OpportunityCommand(MainManager manager, MarsRoverPhotos marsRoverPhotos, KeyboardFactory keyboardFactory) {
        super(manager, "opportunity");
        this.marsRoverPhotos = marsRoverPhotos;
        this.keyboardFactory = keyboardFactory;
    }


    @Override
    public void execute(Long chatId, NasaBot nasaBot) {
        var roverInfo = marsRoverPhotos.constructResponse(this.nameCommand);
        sendRoverInfo(nasaBot, roverInfo, chatId);
    }

    private void sendRoverInfo(NasaBot nasaBot, Optional<String> roverInfoOpt, Long chatId) {
        String roverInfo;
        InlineKeyboardMarkup keyboardMarkup = keyboardFactory.returnToRoversMenu();
        if (roverInfoOpt.isPresent()) {
            roverInfo = roverInfoOpt.get();
        } else {
            roverInfo = "Не вдалося знайти інформацію по цьому марсоходу";
        }

        setAvailableDataPhotos(roverInfo);

        manager.sendTextMessage(chatId, roverInfo, nasaBot, keyboardMarkup);
    }


    private void setAvailableDataPhotos(String roverInfo) {
        var start = "Прибув на Марс: ";
        var end = "Остання дата отриманих знімків: ";

        int startDate = roverInfo.indexOf(start) + start.length();
        int endDate = roverInfo.indexOf(end) + end.length();

        availableDataPhotos[0] = roverInfo.substring(startDate, startDate + 10);
        availableDataPhotos[1] = roverInfo.substring(endDate, endDate + 10);
    }
}

