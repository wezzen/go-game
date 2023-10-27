package com.github.wezzen.base;

import java.util.ArrayList;

public class GameListenerArray implements GameListener {

    private final ArrayList<GameListener> listeners = new ArrayList<>();

    public boolean add(final GameListener listener) {
        return listeners.add(listener);
    }

    public boolean remove(final GameListener listener) {
        return listeners.remove(listener);
    }

    @Override
    public void startGame() {
        for (final GameListener listener : listeners) {
            listener.startGame();
        }
    }

    @Override
    public void playerJoin(final String name, final Color color) {
        for (final GameListener listener : listeners) {
            listener.playerJoin(name, color);
        }
    }

    @Override
    public void playerActed(final String name, final Action action) {
        for (final GameListener listener : listeners) {
            listener.playerActed(name, action);
        }
    }

    @Override
    public void playerPasses(final String name) {
        for (final GameListener listener : listeners) {
            listener.playerPasses(name);
        }
    }

    @Override
    public void gameOver() {
        for (final GameListener listener : listeners) {
            listener.gameOver();
        }
    }
}
