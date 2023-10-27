package com.github.wezzen.go;

import com.github.wezzen.base.Color;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class StoneChainTest {

    @Test
    void createStoneChainTest() {
        assertDoesNotThrow(() -> new StoneChain(Color.BLACK));
        assertDoesNotThrow(() -> new StoneChain(Color.WHITE));
    }

    @Test
    void addStoneTest() {
        final StoneChain chain = new StoneChain(Color.BLACK);
        assertEquals(0, chain.getNumStones());
        final FieldPosition stone = new FieldPosition(0, 0);
        chain.addStone(stone);
        assertEquals(chain, stone.getChain());
        assertEquals(1, chain.getNumStones());
    }

    @Test
    void addLibertiesTest() {
        final StoneChain chain = new StoneChain(Color.BLACK);
        assertEquals(0, chain.getNumLiberties());
        chain.addLiberty(new FieldPosition(0, 0));
        assertEquals(1, chain.getNumLiberties());
    }

    @Test
    void colorTest() {
        final StoneChain chain = new StoneChain(Color.BLACK);
        assertEquals(Color.BLACK, chain.getColor());
    }

}