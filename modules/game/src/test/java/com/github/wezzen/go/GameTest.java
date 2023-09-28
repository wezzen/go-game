package com.github.wezzen.go;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    private static final int GAME_SIZE = 9;

    @Test
    void creatingTest() {
        assertDoesNotThrow(() -> new Game(GAME_SIZE));
    }

    @Test
    void gameThrowsTest() {
        final Game game = new Game(GAME_SIZE);
        game.startGame();
        game.playerJoin("Player1", Color.BLACK);
        assertThrows(IllegalArgumentException.class, () -> game.playerJoin("Player2", Color.BLACK));
        game.playerJoin("Player2", Color.WHITE);
        game.playerActed("Player1", new Action(0, 0));
        assertThrows(IllegalStateException.class, () -> game.playerActed("Player1", new Action(0, 0)));
        assertThrows(IllegalArgumentException.class, () -> game.playerActed("Unknown", new Action(1, 1)));
        game.playerActed("Player2", new Action(1, 1));
        game.playerPasses("Player1");
        game.playerPasses("Player2");
        assertThrows(IllegalStateException.class, () -> game.playerActed("Player1", new Action(5 ,5)));
        game.gameOver();
    }

}