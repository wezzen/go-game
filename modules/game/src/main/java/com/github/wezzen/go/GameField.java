package com.github.wezzen.go;

import com.github.wezzen.base.Action;
import com.github.wezzen.base.Color;
import com.github.wezzen.errors.Error;
import com.github.wezzen.exception.GameException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

public class GameField {

    private final FieldPosition[][] matrix;

    public final int gameSize;

    public GameField(final int gameSize) {
        this.gameSize = gameSize;
        matrix = new FieldPosition[gameSize][gameSize];
        reset();
    }

    void reset() {
        for (int x = 0; x < gameSize; x++) {
            for (int y = 0; y < gameSize; y++) {
                matrix[x][y] = new FieldPosition(x, y);
            }
        }
    }

    private Set<FieldPosition> findPositionsByFilter(final FieldPosition position, Predicate<FieldPosition> predicate) {
        final Set<FieldPosition> emptyPositions = new HashSet<>();
        final int[] dx = {-1, 0, 1, 0}, dy = {0, -1, 0, 1};
        for (int i = 0; i < dx.length; i++) {
            final int x = position.x + dx[i];
            final int y = position.y + dy[i];
            if (isInField(x, y) && predicate.test(matrix[x][y])) {
                emptyPositions.add(matrix[x][y]);
            }
        }
        return emptyPositions;
    }

    private Set<FieldPosition> findNearEmptyPositions(final FieldPosition position) {
        return findPositionsByFilter(position, FieldPosition::isEmpty);
    }

    private Set<FieldPosition> findNearNotEmptyPositions(final FieldPosition position) {
        return findPositionsByFilter(position, p -> !p.isEmpty());
    }

    protected boolean isInField(final int x, final int y) {
        return x >= 0 && x < gameSize && y >= 0 && y < gameSize;
    }

    public void addStone(final int x, final int y, final Color color, final CapturedStones capturedStones) {
        if (!isInField(x, y)) {
            throw new GameException(Error.WRONG_FIELD_POSITION, String.format("Wrong field position at [%d:%d]", x, y));
        }
        if (!matrix[x][y].isEmpty()) {
            throw new GameException(Error.STONE_ALREADY_EXIST, String.format("Stone at position [%d:%d] is already exists", x, y));
        }
        final FieldPosition position = new FieldPosition(x, y);
        final Set<FieldPosition> nearEmptyPositions = findNearEmptyPositions(position);
        final Set<FieldPosition> nearStones = findNearNotEmptyPositions(position);
        if (nearEmptyPositions.isEmpty() && nearStones.stream().noneMatch((stone) -> stone.getChain().getColor() == color)) {
            throw new GameException(Error.SUICIDE_ACTION, String.format("Suicide action at position [%d:%d]", x, y));
        }

        final StoneChain chain = new StoneChain(color);
        for (final FieldPosition near : nearStones) {
            final StoneChain nearChain = near.getChain();
            if (nearChain == null) {
                continue;
            }
            if (nearChain.getColor() == color) {
                // friendly stone
                chain.addLiberty(nearChain.getLiberties());
                chain.addStone(nearChain.getStones());
            } else {
                // opponent stone
                nearChain.removeLiberty(position);
                if (nearChain.getNumLiberties() == 0) {
                    capturedStones.addCapturedStone(nearChain.getStones());
                    nearChain.free();
                }
            }
        }
        chain.addStone(position);
        chain.addLiberty(nearEmptyPositions);
        chain.removeLiberty(position);
        matrix[x][y] = position;
    }

    public FieldPosition getStone(final int x, final int y) {
        return matrix[x][y];
    }

    private boolean isActionAvailable(final int x, final int y, final Color color) {
        if (!isInField(x, y)) {
            return false;
        }
        if (!matrix[x][y].isEmpty()) {
            return false;
        }
        final FieldPosition position = new FieldPosition(x, y);
        final Set<FieldPosition> nearEmptyPositions = findNearEmptyPositions(position);
        final Set<FieldPosition> nearStones = findNearNotEmptyPositions(position);
        return !nearEmptyPositions.isEmpty() || nearStones.stream().anyMatch((stone) -> stone.getChain().getColor() == color);
    }

    public List<Action> getAvailableActions(final Color color) {
        final List<Action> available = new ArrayList<>();
        for (int x = 0; x < gameSize; x++) {
            for (int y = 0; y < gameSize; y++) {
                if (isActionAvailable(x, y, color)) {
                    available.add(new Action(x, y));
                }
            }
        }
        return available;
    }

}
