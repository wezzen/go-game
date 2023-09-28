package com.github.wezzen.go;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Game implements GameListener {

    public static final int NUM_PLAYERS = 2;

    private static final int BLACK_PLAYER_ID = 0;

    private static final int WHITE_PLAYER_ID = 1;

    private final Player[] players = new Player[NUM_PLAYERS];

    private final Map<String, Player> playersByName = new HashMap<>();

    private final boolean[] passes = new boolean[NUM_PLAYERS];

    private int nextPlayerToActId = BLACK_PLAYER_ID;

    private final GameField gameField;

    private boolean isGameActive;

    public Game(final int gameSize) {
        this.gameField = new GameField(gameSize);
    }

    private void reset() {
        Arrays.fill(players, null);
        Arrays.fill(passes, false);
        playersByName.clear();
        nextPlayerToActId = BLACK_PLAYER_ID;
        isGameActive = false;
    }

    private int getPlayerId(final Color color) {
        return color == Color.BLACK ? BLACK_PLAYER_ID : WHITE_PLAYER_ID;
    }

    private Player getPlayerByName(final String name) {
        return playersByName.computeIfAbsent(name, (key) -> {
            throw new IllegalArgumentException("There is no player with name " + key);
        });
    }

    @Override
    public void startGame() {
        reset();
        isGameActive = true;
    }

    @Override
    public void playerJoin(final String name, final Color color) {
        final int playerId = getPlayerId(color);
        if (Objects.nonNull(players[playerId])) {
            throw new IllegalArgumentException("There is another player in game with color " + color.name());
        }
        final Player player = new Player(playerId, name, color);
        players[playerId] = player;
        playersByName.put(name, player);
    }

    @Override
    public void playerActed(final String name, final Action action) {
        if (!isGameActive) {
            throw new IllegalStateException("Game is already over.");
        }
        final Player player = getPlayerByName(name);
        if (!player.equals(players[nextPlayerToActId])) {
            throw new IllegalStateException(String.format("Expected act from %s, but got from %s",
                    players[nextPlayerToActId], player));
        }
        gameField.addStone(action.x, action.y, player.color);
        passes[player.id] = false;
        nextPlayerToActId = (nextPlayerToActId + 1) % NUM_PLAYERS;
    }

    @Override
    public void playerPasses(final String name) {
        final Player player = getPlayerByName(name);
        passes[player.id] = true;
        if (Arrays.equals(new boolean[] {true, true}, passes)) {
            isGameActive = false;
        }
    }

    @Override
    public void gameOver() {
        if (isGameActive) {
            throw new IllegalStateException("Game is still active.");
        }
        //todo find winner.
    }
}
