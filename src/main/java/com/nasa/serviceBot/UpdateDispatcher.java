package com.nasa.serviceBot;

import com.nasa.bot.NasaBot;
import com.nasa.serviceBot.handler.impl.CallbackHandler;
import com.nasa.serviceBot.handler.impl.CommandHandler;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UpdateDispatcher {

    CommandHandler commandHandler;
    CallbackHandler callbackHandler;


    @Autowired
    public UpdateDispatcher(CommandHandler commandHandler, CallbackHandler callbackHandler) {
        this.commandHandler = commandHandler;
        this.callbackHandler = callbackHandler;
    }

    public void handleUpdate(Update update, NasaBot nasaBot) {
        if (update.hasMessage() || update.hasCallbackQuery()) {
            if (update.hasCallbackQuery()) {
                callbackHandler.useUpdate(update, nasaBot);
            } else {
                commandHandler.useUpdate(update, nasaBot);
            }
        } else {
            System.out.println("Отримано оновлення без повідомлення або callback-запиту.");
        }
    }

}




