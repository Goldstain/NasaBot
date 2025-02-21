package com.nasa.serviceBot.callback.impl;

import com.nasa.bot.NasaBot;
import com.nasa.serviceBot.MainManager;
import com.nasa.serviceBot.callback.AbstractCallback;
import com.nasa.serviceBot.keyboard.KeyboardFactory;
import com.nasa.serviceNasaAPI.impl.NewsRSS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CallbackNews extends AbstractCallback {

    private final NewsRSS newsRSS;
    private final KeyboardFactory keyboardFactory;

    @Autowired
    public CallbackNews(MainManager manager, NewsRSS newsRSS, KeyboardFactory keyboardFactory) {
        super(manager, "news");
        this.newsRSS = newsRSS;
        this.keyboardFactory = keyboardFactory;
    }


    @Override
    public void execute(Long chatId, NasaBot nasaBot) {
        sendLatestNews(chatId, nasaBot);
    }

    private void sendLatestNews(Long chatId, NasaBot nasaBot) {
        List<String> news = newsRSS.constructResponse().orElseGet(() -> List.of("Новин немає"));

        StringBuilder newsMessage = new StringBuilder("Останні новини NASA:\n\n");
        for (String item : news) {
            newsMessage.append(item).append("\n\n");
        }

        manager.sendTextMessage(chatId, newsMessage.toString(), nasaBot, keyboardFactory.mainMenuButton());
    }
}
