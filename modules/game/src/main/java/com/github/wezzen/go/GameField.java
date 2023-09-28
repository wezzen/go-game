package com.github.wezzen.go;

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


    public void addStone(final int x, final int y, final Color color) {

    }

}
