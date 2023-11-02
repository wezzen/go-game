package com.github.wezzen.go;

import com.github.wezzen.base.Color;

/**
 * Info about player.
 */
public class PlayerInfo {

    public final int id;

    public final String name;

    public final Color color;

    private final CapturedStones capturedStones = new CapturedStones();

    public PlayerInfo(final int id, final String name, final Color color) {
        this.id = id;
        this.name = name;
        this.color = color;
    }

    public CapturedStones getCapturedStones() {
        return capturedStones;
    }
}
