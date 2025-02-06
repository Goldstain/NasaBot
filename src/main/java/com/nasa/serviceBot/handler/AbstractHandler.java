package com.nasa.serviceBot.handler;

import com.nasa.bot.NasaBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface AbstractHandler {

    void useUpdate(Update update, NasaBot nasaBot);

}
