package com.github.wezzen.go;

/**
 * Info about player.
 */
public class PlayerInfo {

    public final int id;

    public final String name;

    public final Color color;

    public PlayerInfo(final int id, final String name, final Color color) {
        this.id = id;
        this.name = name;
        this.color = color;
    }
}
