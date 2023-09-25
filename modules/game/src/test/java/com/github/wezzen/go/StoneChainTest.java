package com.github.wezzen.go;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class StoneChainTest {

    private static final Player mock = Mockito.mock(Player.class);

    @Test
    void createStoneChainTest() {
        assertDoesNotThrow(() -> new StoneChain(mock));
    }

    @Test
    void addStoneTest() {
        final StoneChain chain = new StoneChain(mock);
        assertEquals(0, chain.getNumStones());
        final FieldPosition stone = new FieldPosition(0, 0);
        chain.addStone(stone);
        assertEquals(chain, stone.getChain());
        assertEquals(1, chain.getNumStones());
    }

    @Test
    void addLibertiesTest() {
        final StoneChain chain = new StoneChain(mock);
        assertEquals(0, chain.getNumLiberties());
        chain.addLiberty(new FieldPosition(0, 0));
        assertEquals(1, chain.getNumLiberties());
    }

    @Test
    void ownerTest() {
        final StoneChain chain = new StoneChain(mock);
        assertEquals(mock, chain.getOwner());
    }

}