package com.github.wezzen.base;

public interface GameListener {

    void startGame();

    void playerJoin(final String name, final Color color);

    void playerActed(final String name, final Action action);

    void playerPasses(final String name);

    void gameOver();

}
