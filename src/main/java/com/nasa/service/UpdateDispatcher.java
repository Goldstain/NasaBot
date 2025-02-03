package com.nasa.service;

import com.nasa.service.handler.impl.CallbackHandler;
import com.nasa.service.handler.impl.CommandHandler;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
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

    public void handleUpdate(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            commandHandler.useUpdate(update);
        } else if (update.hasCallbackQuery()) {
            callbackHandler.useUpdate(update);

        }
    }

}
