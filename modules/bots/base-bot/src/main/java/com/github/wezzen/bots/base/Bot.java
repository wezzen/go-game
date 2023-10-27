package com.github.wezzen.bots.base;

import com.github.wezzen.base.Action;
import com.github.wezzen.base.Color;
import com.github.wezzen.base.GoPlayer;
import com.github.wezzen.go.Game;

import java.util.Random;

public abstract class Bot implements GoPlayer {


    public abstract static class BotFactory {
        public abstract Bot createBot(final Game game, final Color color);
    }

    protected final Game game;

    protected final Random rnd;

    protected final Color color;

    public Bot(final Game game, final Color color) {
        this.game = game;
        this.color = color;
        rnd = new Random(System.currentTimeMillis());
    }

    public Bot(final Bot bot) {
        this.game = bot.game.getCopy();
        this.color = bot.color;
        rnd = new Random(bot.rnd.nextLong());
    }

    protected int getRandom(final int min, final int max) {
        return rnd.nextInt((max - min) + 1) + min;
    }

    @Override
    public void startGame() {
    }

    @Override
    public void playerJoin(final String name, final Color color) {
    }

    @Override
    public void playerActed(final String name, final Action action) {
    }

    @Override
    public void playerPasses(final String name) {
    }

    @Override
    public void gameOver() {
    }
}
