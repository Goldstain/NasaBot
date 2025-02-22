package com.nasa.serviceBot;

import com.nasa.bot.NasaBot;
import com.nasa.exception.UpdateNotFoundException;
import com.nasa.serviceBot.handler.Handler;
import com.nasa.serviceBot.handler.impl.CallbackHandler;
import com.nasa.serviceBot.handler.impl.CommandHandler;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UpdateDispatcher {

    Logger logger = LoggerFactory.getLogger(UpdateDispatcher.class);

    Handler commandHandler;
    Handler callbackHandler;


    @Autowired
    public UpdateDispatcher(CommandHandler commandHandler, CallbackHandler callbackHandler) {
        this.commandHandler = commandHandler;
        this.callbackHandler = callbackHandler;
    }

    public void handleUpdate(Update update, NasaBot nasaBot) throws UpdateNotFoundException {
        if (update.hasMessage() || update.hasCallbackQuery()) {
            if (update.hasCallbackQuery()) {
                callbackHandler.useUpdate(update, nasaBot);
            } else {
                commandHandler.useUpdate(update, nasaBot);
                getUserInfo(update);
            }
        } else {
            throw new UpdateNotFoundException("Отримано оновлення без повідомлення або callback-запиту.");
        }
    }

    private void getUserInfo(Update update) {
        Message message = update.getMessage();
        Long userId = message.getFrom().getId();
        String firstName = message.getFrom().getFirstName();
        String lastName = message.getFrom().getLastName();
        String userName = message.getFrom().getUserName();
        String text = message.getText();

        String logText = String.format("ID: %d User: %s %s %s Message: %s"
                , userId, firstName, lastName, userName, text);
        logger.info(logText);
    }

}




