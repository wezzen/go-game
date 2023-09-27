package com.github.wezzen.go;

import java.util.Objects;

public class FieldPosition {

    public final int x;

    public final int y;

    private StoneChain chain = null;

    public FieldPosition(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    public void setChain(final StoneChain chain) {
        this.chain = chain;
    }

    public StoneChain getChain() {
        return chain;
    }

    @Override
    public String toString() {
        return "{x: " + x + ", y: " + y + (chain == null ? ", empty}" : ", chain: " + chain.getId() + "}");
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        final FieldPosition that = (FieldPosition) o;
        return x == that.x && y == that.y && Objects.equals(chain, that.chain);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, chain);
    }
}
