package com.github.wezzen.go;

import com.github.wezzen.base.Action;
import com.github.wezzen.base.Color;
import com.github.wezzen.base.GameListenerAdapter;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class GameFieldPrinter extends GameListenerAdapter {

    private static final String TOP_LEFT_CORNER = "┌";

    private static final String BOTTOM_LEFT_CORNER = "└";

    private static final String BOTTOM_RIGHT_CORNER = "┘";

    private static final String TOP_RIGHT_CORNER = "┐";

    private static final String HORIZONTAL_LINE = "─";

    private static final String LEFT_VERTICAL_AND_RIGHT = "├";

    private static final String RIGHT_VERTICAL_AND_LEFT = "┤";

    private static final String BOTTOM_HORIZONTAL_AND_TOP = "┴";

    private static final String TOP_HORIZONTAL_AND_BOTTOM = "┬";

    private static final String VERTICAL_AND_HORIZONTAL = "┼";

    private static final String WHITE_STONE = "○";

    private static final String BLACK_STONE = "●";

    private final int gameSize;

    private final Map<String, String> playerToColorMap = new HashMap<>();

    private StringBuilder stringBuilder;

    public GameFieldPrinter(final int gameSize) {
        this.gameSize = gameSize;
    }

    private StringBuilder buildStringField(final int size) {
        final StringBuilder builder = new StringBuilder();
        for (int y = 0; y < size; y++) {
            if (y == 0) {
                builder.append(TOP_LEFT_CORNER).append(HORIZONTAL_LINE);
            } else if (y == size - 1) {
                builder.append(BOTTOM_LEFT_CORNER).append(HORIZONTAL_LINE);
            } else {
                builder.append(LEFT_VERTICAL_AND_RIGHT).append(HORIZONTAL_LINE);
            }
            for (int x = 0; x < size - 2; x++) {
                if (y == 0) {
                    builder.append(HORIZONTAL_LINE).append(TOP_HORIZONTAL_AND_BOTTOM).append(HORIZONTAL_LINE);
                } else if (y == size - 1) {
                    builder.append(HORIZONTAL_LINE).append(BOTTOM_HORIZONTAL_AND_TOP).append(HORIZONTAL_LINE);
                } else {
                    builder.append(HORIZONTAL_LINE).append(VERTICAL_AND_HORIZONTAL).append(HORIZONTAL_LINE);
                }
            }
            builder.append(HORIZONTAL_LINE);
            if (y == 0) {
                builder.append(TOP_RIGHT_CORNER);
            } else if (y == size - 1) {
                builder.append(BOTTOM_RIGHT_CORNER);
            } else {
                builder.append(RIGHT_VERTICAL_AND_LEFT);
            }
            builder.append('\n');
        }
        return builder;
    }

    private int calculateIndexToInsert(final Action action) {
        return action.y * (gameSize * 3 - 1) + (action.x * 3);
    }

    private void checkActionSize(final int c, Supplier<String> message) {
        if (c >= gameSize) {
            throw new IllegalArgumentException(message.get());
        }
    }

    @Override
    public String toString() {
        return stringBuilder.toString();
    }

    @Override
    public void startGame() {
        stringBuilder = buildStringField(gameSize);
        playerToColorMap.clear();
    }

    @Override
    public void playerJoin(final String name, final Color color) {
        super.playerJoin(name, color);
        final String stringColor = color == Color.BLACK ? BLACK_STONE : WHITE_STONE;
        playerToColorMap.put(name, stringColor);
    }

    @Override
    public void playerActed(final String name, final Action action) {
        super.playerActed(name, action);
        checkActionSize(action.x, () -> String.format("x (%d) is greater than gameSize (%d)", action.x, gameSize));
        checkActionSize(action.y, () -> String.format("y (%d) is greater than gameSize (%d)", action.y, gameSize));
        final String color = playerToColorMap.computeIfAbsent(name, (playerName) -> {
            throw new IllegalArgumentException(String.format("Player with name [%s] is unknown", playerName));
        });
        final int index = calculateIndexToInsert(action);
        stringBuilder.deleteCharAt(index);
        stringBuilder.insert(index, color);
    }
}
