package com.github.wezzen.errors;

import com.github.wezzen.exception.GameException;

public enum Error {

    STONE_ALREADY_EXIST(1001, "Stone at position"),

    WRONG_FIELD_POSITION(1002, "Wrong field position"),

    SUICIDE_ACTION(1003, "Suicide action"),

    LIBERTY_POSITION_HAS_AN_OWNER(1004, "Lbierty field position has an owner")
    ;

    public final int errorCode;

    public final String message;

    Error(final int errorCode, final String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
}
