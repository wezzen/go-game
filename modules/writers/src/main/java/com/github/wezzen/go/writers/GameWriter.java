package com.github.wezzen.go.writers;

import com.github.wezzen.base.*;
import com.github.wezzen.go.GameFieldPrinter;

import java.io.Closeable;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

public class GameWriter extends GameListenerArray implements Closeable {

    private final Writer out;

    private final StringBuilder line = new StringBuilder();

    final GameFieldPrinter fieldPrinter;

    public GameWriter(final String fileName, final GameFieldPrinter fieldPrinter) throws IOException {
        out = new FileWriter(fileName, StandardCharsets.UTF_8);
        this.fieldPrinter = fieldPrinter;
        add(fieldPrinter);
    }

    @Override
    public void startGame() {
        super.startGame();
        line.append("# GAME STARTED #").append("\n\n");
    }

    @Override
    public void playerJoin(final String name, final Color color) {
        super.playerJoin(name, color);
        line.append("# PLAYER JOINED: [").append(name).append(" ").append(color.name()).append("] #\n\n");
    }

    @Override
    public void playerActed(final String name, final Action action) {
        super.playerActed(name, action);
        line.append("# PLAYER [").append(name).append("] ACTED ").append(action.toString()).append(" #\n");
        line.append(fieldPrinter.toString()).append("\n\n");
    }

    @Override
    public void playerPasses(final String name) {
        super.playerPasses(name);
        line.append("# PLAYER [").append(name).append("] PASSES #\n\n");
    }

    @Override
    public void gameOver() {
        super.gameOver();
        line.append("# GAME IS OVER #\n\n");
    }

    public void flush() throws IOException {
        out.write(line.toString());
        out.flush();
        line.setLength(0);
    }

    @Override
    public void close() throws IOException {
        out.close();
    }
}
