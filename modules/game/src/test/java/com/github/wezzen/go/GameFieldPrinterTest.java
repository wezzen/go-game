package com.github.wezzen.go;

import com.github.wezzen.base.Action;
import com.github.wezzen.base.Color;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameFieldPrinterTest {

    @Test
    void notThrowExceptionTest() {
        final GameFieldPrinter gameFieldPrinter = new GameFieldPrinter(Game.DEFAULT_GAME_SIZE);
        gameFieldPrinter.startGame();
        gameFieldPrinter.playerJoin("Player1", Color.BLACK);
        gameFieldPrinter.playerJoin("Player2", Color.WHITE);
        gameFieldPrinter.playerActed("Player1", new Action(0, 0));
        gameFieldPrinter.playerActed("Player2", new Action(1, 1));
        gameFieldPrinter.playerPasses("Player1");
        gameFieldPrinter.playerPasses("Player2");
        gameFieldPrinter.gameOver();
        assertDoesNotThrow(gameFieldPrinter::toString);
    }

}