package com.nasa.bot;

import com.nasa.config.BotConfig;
import com.nasa.exception.UpdateNotFoundException;
import com.nasa.serviceBot.UpdateDispatcher;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NasaBot extends TelegramLongPollingBot {

    Logger logger = LoggerFactory.getLogger(NasaBot.class);

    BotConfig botConfig;
    UpdateDispatcher updateDispatcher;

    @Autowired
    public NasaBot(BotConfig botConfig, UpdateDispatcher updateDispatcher) {
        super(botConfig.getToken());
        this.botConfig = botConfig;
        this.updateDispatcher = updateDispatcher;
    }


    @Override
    public void onUpdateReceived(Update update) {
        try {
            updateDispatcher.handleUpdate(update, this);
        } catch (UpdateNotFoundException e) {
            logger.error(update + " not found" + e.getMessage());
        }
    }


    @Override
    public String getBotUsername() {
        return botConfig.getUsername();
    }


}
