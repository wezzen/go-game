package com.github.wezzen.base;

public class Action {
    public final int x;

    public final int y;

    public Action(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "x: " + x + ", y: " + y;
    }
}
