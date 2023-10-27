package com.github.wezzen.go;

import com.github.wezzen.base.Color;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class StoneChain {

    private static final AtomicInteger igGenerator = new AtomicInteger(0);

    private final int id;

    private final Set<FieldPosition> stones = new HashSet<>();

    private final Set<FieldPosition> liberties = new HashSet<>();

    private final Color color;

    public StoneChain(final Color color) {
        this.color = color;
        id = igGenerator.getAndIncrement();
    }

    public int getId() {
        return id;
    }

    public void addStone(final FieldPosition position) {
        stones.add(position);
        position.setChain(this);
    }

    public void addStone(final Set<FieldPosition> positions) {
        positions.forEach(this::addStone);
    }

    public void addLiberty(final FieldPosition position) {
        liberties.add(position);
    }

    public void addLiberty(final Set<FieldPosition> positions) {
        liberties.addAll(positions);
    }

    public Set<FieldPosition> getLiberties() {
        return liberties;
    }

    public Set<FieldPosition> getStones() {
        return stones;
    }

    public void removeLiberty(final FieldPosition position) {
        liberties.remove(position);
    }

    public int getNumStones() {
        return stones.size();
    }

    public int getNumLiberties() {
        return liberties.size();
    }

    public Color getColor() {
        return color;
    }

}
