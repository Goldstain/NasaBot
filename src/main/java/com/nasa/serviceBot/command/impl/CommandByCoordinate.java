package com.nasa.serviceBot.command.impl;

import com.nasa.bot.NasaBot;
import com.nasa.serviceBot.MainManager;
import com.nasa.serviceBot.command.AbstractCommand;
import com.nasa.serviceBot.keyboard.KeyboardFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class CommandByCoordinate extends AbstractCommand {

    private final KeyboardFactory keyboardFactory;

    @Autowired
    public CommandByCoordinate(MainManager manager, KeyboardFactory keyboardFactory) {
        super(manager, "Отримати по координатам");
        this.keyboardFactory = keyboardFactory;
    }

    @Override
    public void execute(Message message, NasaBot nasaBot) {
        manager.sendTextMessage(message.getChatId(), "Введіть широту і довготу за зразком:\n" +
                "50 31 або -50.004505 -36.233709", nasaBot, keyboardFactory.mainMenuButton());
    }
}
