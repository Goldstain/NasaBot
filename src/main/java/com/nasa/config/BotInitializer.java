package com.nasa.config;

import com.nasa.bot.NasaBot;
import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import org.telegram.telegrambots.updatesreceivers.DefaultWebhook;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BotInitializer {

    TelegramBotsApi telegramBotsApi;
    NasaBot nasaBot;

    @Autowired
    public BotInitializer(NasaBot nasaBot) throws TelegramApiException {
        this.nasaBot = nasaBot;
        this.telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);

    }

    @PostConstruct
    public void init() throws TelegramApiException {
        telegramBotsApi.registerBot(nasaBot);
    }
}
