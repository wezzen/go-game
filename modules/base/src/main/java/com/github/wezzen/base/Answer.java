package com.github.wezzen.base;

public class Answer {

    public final Type type;

    public final Action action;

    public Answer(final Type type, final Action action) {
        this.type = type;
        this.action = action;
    }

    public enum Type {
        ACTION, PASS
    }
}
