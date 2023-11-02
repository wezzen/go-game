package com.github.wezzen.go;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class CapturedStones {

    private final List<FieldPosition> captured = new ArrayList<>();

    void addCapturedStone(final FieldPosition captured) {
        this.captured.add(Objects.requireNonNull(captured));
    }

    void addCapturedStone(final Collection<FieldPosition> captured) {
        if (captured.size() < 1) {
            throw new IllegalArgumentException("num of captured stones should be greater than 0.");
        }
        this.captured.addAll(captured);
    }

    public int getNumCapturedStones() {
        return captured.size();
    }

}
