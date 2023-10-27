package com.github.wezzen.selfplay;

import com.github.wezzen.base.GameListenerArray;
import com.github.wezzen.go.Game;

public class GameGroup extends GameListenerArray {

    private final Game game;

    public GameGroup(final Game game) {
        this.game = game;
        add(game);
    }

}
