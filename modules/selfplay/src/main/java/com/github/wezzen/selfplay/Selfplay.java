package com.github.wezzen.selfplay;

import com.github.wezzen.base.Answer;
import com.github.wezzen.base.Color;
import com.github.wezzen.base.GoPlayer;
import com.github.wezzen.bots.base.Bot;
import com.github.wezzen.bots.random.RandomBot;
import com.github.wezzen.go.Game;
import com.github.wezzen.go.writers.GameWriter;

import java.io.IOException;
import java.util.UUID;

public class Selfplay {

    private final Game game;

    private final Bot.BotFactory[] factories;

    public Selfplay(final int gameSize, final Bot.BotFactory[] factories) {
        game = new Game(gameSize);
        if (factories.length != Game.NUM_PLAYERS) {
            throw new IllegalArgumentException(
                    String.format("Invalid number of players. Expected %d, actual %d", Game.NUM_PLAYERS, factories.length)
            );
        }
        this.factories = factories;
    }

    public void playSomeGames(final int numGames) throws IOException {
        final Player[] players = new Player[Game.NUM_PLAYERS];
        final GameWriter writer = new GameWriter("selfplay.log");
        for (int gameId = 0; gameId < numGames; gameId++) {
            final Bot blackBot = factories[0].createBot(game, Color.BLACK);
            final Bot whiteBot = factories[1].createBot(game, Color.WHITE);
            final GameGroup gameGroup = new GameGroup(game);
            gameGroup.add(writer);
            gameGroup.add(blackBot);
            gameGroup.add(whiteBot);
            players[0] = new Player("BLACK", blackBot);
            players[1] = new Player("WHITE", whiteBot);
            gameGroup.startGame();
            gameGroup.playerJoin(players[0].name, Color.BLACK);
            gameGroup.playerJoin(players[1].name, Color.WHITE);
            while (game.isGameActive()) {
                final int nextPlayerToActId = game.getNextPlayerToActId();
                final Player needToActPlayer = players[nextPlayerToActId];
                final Answer answer = needToActPlayer.player.getAnswer();
                if (answer.type == Answer.Type.ACTION) {
                    gameGroup.playerActed(needToActPlayer.name, answer.action);
                } else {
                    gameGroup.playerPasses(needToActPlayer.name);
                }
            }
            gameGroup.gameOver();
            writer.flush();
            System.out.printf("ends %d game of %d\n", gameId + 1, numGames);
        }
    }

    private static final class Player {
        public final String name;
        public final GoPlayer player;

        private Player(final String name, final GoPlayer player) {
            this.name = name;
            this.player = player;
        }
    }

    public static void main(final String[] args) throws IOException {
        final int gameSize = Integer.parseInt(args[0]);
        final int numGames = Integer.parseInt(args[1]);
        final Selfplay selfplay = new Selfplay(gameSize, new Bot.BotFactory[]{
                new RandomBot.RandomBotFactory(),
                new RandomBot.RandomBotFactory(),
        });
        selfplay.playSomeGames(numGames);
    }
}
