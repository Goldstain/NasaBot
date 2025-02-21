package com.nasa.serviceBot.command;

import com.nasa.bot.NasaBot;

public interface CallbackCommand {

    void execute(Long chatId, NasaBot nasaBot);

    String getNameCommand();



}
