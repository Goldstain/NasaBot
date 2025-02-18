package com.nasa.bot;

import com.nasa.config.BotConfig;
import com.nasa.serviceBot.UpdateDispatcher;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NasaBot extends TelegramLongPollingBot {

    BotConfig botConfig;
    UpdateDispatcher updateDispatcher;
    DefaultBotOptions defaultBotOptions;

    @Autowired
    public NasaBot( BotConfig botConfig, UpdateDispatcher updateDispatcher) {
        super( botConfig.getToken());
        this.botConfig = botConfig;
        this.updateDispatcher = updateDispatcher;
    }


    @Override
    public void onUpdateReceived(Update update) {
        updateDispatcher.handleUpdate(update, this);
    }


    @Override
    public String getBotUsername() {
        return botConfig.getUsername();
    }


}
