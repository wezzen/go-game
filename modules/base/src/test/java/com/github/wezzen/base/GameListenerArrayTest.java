package com.github.wezzen.base;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class GameListenerArrayTest {

    @Test
    void listenerArrayEventsTest() {
        final String mockPlayerName = "MockPlayerName";
        final Color mockColor = Mockito.mock(Color.class);
        final GameListener spyGameListener = Mockito.spy(GameListener.class);
        final Action mockAction = Mockito.mock(Action.class);
        final GameListenerArray array = Mockito.spy(new GameListenerArray());
        array.add(spyGameListener);
        array.startGame();
        array.playerJoin(mockPlayerName, mockColor);
        array.playerActed(mockPlayerName, mockAction);
        array.playerPasses(mockPlayerName);
        array.gameOver();

        Mockito.verify(array, Mockito.times(1)).startGame();
        Mockito.verify(array, Mockito.times(1)).playerJoin(Mockito.any(), Mockito.any());
        Mockito.verify(array, Mockito.times(1)).playerActed(Mockito.any(), Mockito.any());
        Mockito.verify(array, Mockito.times(1)).playerPasses(Mockito.any());
        Mockito.verify(array, Mockito.times(1)).gameOver();
    }


    @Test
    void addAndDeleteListenersTest() {
        final GameListener mockGameListener = Mockito.mock(GameListener.class);
        final GameListenerArray array = new GameListenerArray();
        assertFalse(array.remove(mockGameListener));
        assertTrue(array.add(mockGameListener));
        assertTrue(array.remove(mockGameListener));
    }

}