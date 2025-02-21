package com.nasa.serviceBot.command;

import com.nasa.serviceBot.MainManager;

public abstract class AbstractCommand implements Command {

    protected MainManager manager;
    protected String nameCommand;

    public AbstractCommand(MainManager manager, String nameCommand) {
        this.manager = manager;
        this.nameCommand = nameCommand;
    }


    @Override
    public String getNameCommand() {
        return nameCommand;
    }
}
