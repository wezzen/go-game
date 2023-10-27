package com.github.wezzen.go;

import com.github.wezzen.base.Color;
import com.github.wezzen.errors.Error;
import com.github.wezzen.exception.GameException;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

public class GameField {

    private final FieldPosition[][] matrix;

    public final int gameSize;

    private int emptyPlaces;

    private int filedPlaces;

    public GameField(final int gameSize) {
        this.gameSize = gameSize;
        matrix = new FieldPosition[gameSize][gameSize];
        reset();
    }

    public int getNumPlacedStones() {
        return filedPlaces;
    }

    public int getNumEmptyPlaces() {
        return emptyPlaces;
    }

    void reset() {
        for (int x = 0; x < gameSize; x++) {
            for (int y = 0; y < gameSize; y++) {
                matrix[x][y] = new FieldPosition(x, y);
            }
        }
        emptyPlaces = gameSize * gameSize;
        filedPlaces = 0;
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

    public void addStone(final int x, final int y, final Color color) {
        if (!isInField(x, y)) {
            throw new GameException(Error.WRONG_FIELD_POSITION, String.format("Wrong field position at [%d:%d]", x, y));
        }
        if (!matrix[x][y].isEmpty()) {
            throw new GameException(Error.STONE_ALREADY_EXIST, String.format("Stone at position [%d:%d] is already exists", x, y));
        }
        final FieldPosition position = new FieldPosition(x, y);
        final Set<FieldPosition> nearEmptyPositions = findNearEmptyPositions(position);
        if (nearEmptyPositions.isEmpty()) {
            throw new GameException(Error.SUICIDE_ACTION, String.format("Suicide action at position [%d:%d]", x, y));
        }
        final Set<FieldPosition> nearStones = findNearNotEmptyPositions(position);
        final StoneChain chain = new StoneChain(color);
        for (final FieldPosition near : nearStones) {
            final StoneChain nearChain = near.getChain();
            if (nearChain.getColor() == color) {
                // friendly stone
                chain.addLiberty(nearChain.getLiberties());
                chain.addStone(nearChain.getStones());
            } else {
                // opponent stone
                nearChain.removeLiberty(position);
            }
        }
        chain.addStone(position);
        chain.addLiberty(nearEmptyPositions);
        chain.removeLiberty(position);
        matrix[x][y] = position;
        filedPlaces++;
        emptyPlaces--;
    }

    public FieldPosition getStone(final int x, final int y) {
        return matrix[x][y];
    }

    public boolean isActionAvailable(final int x, final int y) {
        if (!isInField(x, y)) {
            return false;
        }
        if (!matrix[x][y].isEmpty()) {
            return false;
        }
        final Set<FieldPosition> nearEmptyPositions = findNearEmptyPositions(new FieldPosition(x, y));
        return !nearEmptyPositions.isEmpty();
    }

}
