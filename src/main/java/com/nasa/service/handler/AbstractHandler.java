package com.nasa.service.handler;

import com.nasa.bot.NasaBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public abstract class AbstractHandler {

    public abstract void useUpdate(Update update);

}
