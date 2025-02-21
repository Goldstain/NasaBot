package com.nasa.serviceBot.callback;

import com.nasa.bot.NasaBot;

public interface Callback {

    void execute(Long chatId, NasaBot nasaBot);

    String getNameCallback();


}
