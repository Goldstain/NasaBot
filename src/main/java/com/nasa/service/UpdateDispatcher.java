package com.nasa.service;

import com.nasa.bot.NasaBot;
import com.nasa.service.handler.impl.CallbackHandler;
import com.nasa.service.handler.impl.CommandHandler;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

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
        if (update.hasMessage() || update.hasCallbackQuery()) {

            if (update.hasCallbackQuery()) {
                callbackHandler.useUpdate(update);
            } else {
                commandHandler.useUpdate(update);
            }
        }
    }

}
