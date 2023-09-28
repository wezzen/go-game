package com.github.wezzen.go;

/**
 * Info about player.
 */
public class Player {

    public final int id;

    public final String name;

    public final Color color;

    public Player(final int id, final String name, final Color color) {
        this.id = id;
        this.name = name;
        this.color = color;
    }
}
