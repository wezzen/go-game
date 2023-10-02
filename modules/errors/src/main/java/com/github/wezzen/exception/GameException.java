package com.github.wezzen.exception;

import com.github.wezzen.errors.Error;

public class GameException extends RuntimeException {

    public final Error error;

    public GameException(final Error error, final String message) {
        super(message);
        this.error = error;
    }
}
