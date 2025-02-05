package com.nasa.serviceBot.handler;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface AbstractHandler {

    void useUpdate(Update update);

}
