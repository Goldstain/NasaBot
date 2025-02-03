package com.nasa.bot;

import com.nasa.config.BotConfig;
import com.nasa.service.UpdateDispatcher;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NasaBot extends TelegramLongPollingBot {

    BotConfig botConfig;
    UpdateDispatcher updateDispatcher;


    @Autowired
    public NasaBot(BotConfig botConfig, UpdateDispatcher updateDispatcher) {
        this.botConfig = botConfig;
        this.updateDispatcher = updateDispatcher;
    }


    @Override
    public void onUpdateReceived(Update update) {
        updateDispatcher.handleUpdate(update);
    }

    @Override
    public String getBotUsername() {
        return botConfig.getUsername();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }
}
