package com.nasa.serviceBot.command;

import com.nasa.serviceBot.MainManager;

public abstract class AbstractCallbackCommand implements CallbackCommand {

    protected final MainManager manager;

    protected String nameCommand;

    public AbstractCallbackCommand(MainManager manager, String nameCommand) {
        this.manager = manager;
        this.nameCommand = nameCommand;
    }

    @Override
    public String getNameCommand() {
        return nameCommand;
    }
}
