package com.nasa.serviceBot.callback;

import com.nasa.serviceBot.MainManager;

public abstract class AbstractCallback implements Callback {

    protected final MainManager manager;

    protected String nameCallback;

    public AbstractCallback(MainManager manager, String nameCallback) {
        this.manager = manager;
        this.nameCallback = nameCallback;
    }

    @Override
    public String getNameCallback() {
        return nameCallback;
    }
}
