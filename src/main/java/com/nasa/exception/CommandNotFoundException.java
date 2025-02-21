package com.nasa.exception;

public class CommandNotFoundException extends Exception {
    public CommandNotFoundException() {
        super("Command not found");
    }
}
