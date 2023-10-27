package com.github.wezzen.go;

import com.github.wezzen.base.Color;
import com.github.wezzen.exception.GameException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameFieldTest {

    @Test
    void isInFieldTest() {
        final GameField field4 = new GameField(4);
        assertTrue(field4.isInField(0, 0));
        assertFalse(field4.isInField(-1, 0));
        assertFalse(field4.isInField(0, -1));
        assertTrue(field4.isInField(3, 3));
        assertFalse(field4.isInField(3, 4));
        assertFalse(field4.isInField(4, 3));
    }

    @Test
    void addStoneSimpleTest() {
        final GameField field = new GameField(Game.DEFAULT_GAME_SIZE);
        assertTrue(field.getStone(0, 0).isEmpty());
        field.addStone(0, 0, Color.BLACK);
        assertFalse(field.getStone(0, 0).isEmpty());
    }

    @Test
    void wrongPositionStoneThrowsTest() {
        final GameField field = new GameField(Game.DEFAULT_GAME_SIZE);
        assertThrows(GameException.class, () -> field.addStone(-1, 0, Color.BLACK));
        assertThrows(GameException.class, () -> field.addStone(0, -1, Color.BLACK));
        assertThrows(GameException.class, () -> field.addStone(-1, -1, Color.BLACK));
        assertThrows(GameException.class, () -> field.addStone(0, Game.DEFAULT_GAME_SIZE, Color.BLACK));
        assertThrows(GameException.class, () -> field.addStone(Game.DEFAULT_GAME_SIZE, 0, Color.BLACK));
        assertThrows(GameException.class, () -> field.addStone(Game.DEFAULT_GAME_SIZE, Game.DEFAULT_GAME_SIZE, Color.BLACK));
    }

    @Test
    void addPositionOnNotEmptyPositionThowsTest() {
        final GameField field = new GameField(Game.DEFAULT_GAME_SIZE);
        field.addStone(5, 5, Color.WHITE);
        assertThrows(GameException.class, () -> field.addStone(5, 5, Color.WHITE));
        assertThrows(GameException.class, () -> field.addStone(5, 5, Color.BLACK));
    }

    @Test
    void addStoneSameChainTest() {
        final GameField field = new GameField(Game.DEFAULT_GAME_SIZE);
        field.addStone(5, 5, Color.BLACK);
        field.addStone(5, 6, Color.BLACK);
        field.addStone(5, 7, Color.BLACK);
        assertEquals(3, field.getStone(5, 5).getChain().getNumStones());
        assertEquals(8, field.getStone(5, 5).getChain().getNumLiberties());
        field.addStone(6, 6, Color.BLACK);
        assertEquals(4, field.getStone(6, 6).getChain().getNumStones());
        assertEquals(8, field.getStone(6, 6).getChain().getNumLiberties());
        assertEquals(field.getStone(5, 5).getChain().getId(), field.getStone(6, 6).getChain().getId());
    }

    @Test
    void addStoneNearOpponentsStonesTest() {
        final GameField field = new GameField(Game.DEFAULT_GAME_SIZE);
        field.addStone(1, 1, Color.BLACK);
        field.addStone(1, 2, Color.BLACK);
        field.addStone(1, 3, Color.BLACK);
        assertEquals(3, field.getStone(1, 1).getChain().getNumStones());
        assertEquals(8, field.getStone(1, 1).getChain().getNumLiberties());
        field.addStone(2, 3, Color.WHITE);
        assertEquals(3, field.getStone(1, 1).getChain().getNumStones());
        assertEquals(7, field.getStone(1, 1).getChain().getNumLiberties());
        assertEquals(1, field.getStone(2, 3).getChain().getNumStones());
        assertEquals(3, field.getStone(2, 3).getChain().getNumLiberties());
    }

    @Test
    void addSuicideStoneTest() {
        final GameField field = new GameField(Game.DEFAULT_GAME_SIZE);
        field.addStone(4, 5, Color.BLACK);
        field.addStone(5, 4, Color.BLACK);
        field.addStone(6, 5, Color.BLACK);
        field.addStone(5, 6, Color.BLACK);
        assertThrows(GameException.class, () -> field.addStone(5, 5, Color.WHITE));
    }

}