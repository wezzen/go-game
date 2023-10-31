package com.github.wezzen.bots.random;

import com.github.wezzen.base.Action;
import com.github.wezzen.base.Color;
import com.github.wezzen.bots.base.Bot;
import com.github.wezzen.go.Game;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RandomBotTest {

    private static Bot.BotFactory factory = null;

    @BeforeAll
    static void init() {
        factory = new RandomBot.RandomBotFactory();
    }

    private Bot.BotFactory getFactory() {
        return factory;
    }

    @Test
    void getAnswerTest() {
        final Game game = new Game(Game.DEFAULT_GAME_SIZE);
        final Color color = Color.BLACK;
        final Bot bot = getFactory().createBot(game, color);
        assertDoesNotThrow(bot::getAnswer);
    }

    @Test
    void nameTest() {
        final Game game = new Game(Game.DEFAULT_GAME_SIZE);
        final Color color = Color.BLACK;
        final Bot bot = getFactory().createBot(game, color);
        assertEquals(RandomBot.class.getSimpleName(), bot.getName());
    }

    @Test
    void eventsTest() {
        final Game game = new Game(Game.DEFAULT_GAME_SIZE);
        final Color color = Color.BLACK;
        final Bot spyBot = Mockito.spy(getFactory().createBot(game, color));
        final String mockPlayerName = "MockPlayerName";
        final Color mockColor = Mockito.mock(Color.class);
        final Action mockAction = Mockito.mock(Action.class);
        spyBot.startGame();
        spyBot.playerJoin(mockPlayerName, mockColor);
        spyBot.playerActed(mockPlayerName, mockAction);
        spyBot.playerPasses(mockPlayerName);
        spyBot.gameOver();

        Mockito.verify(spyBot, Mockito.times(1)).startGame();
        Mockito.verify(spyBot, Mockito.times(1)).playerJoin(Mockito.any(), Mockito.any());
        Mockito.verify(spyBot, Mockito.times(1)).playerActed(Mockito.any(), Mockito.any());
        Mockito.verify(spyBot, Mockito.times(1)).playerPasses(Mockito.any());
        Mockito.verify(spyBot, Mockito.times(1)).gameOver();
    }

}