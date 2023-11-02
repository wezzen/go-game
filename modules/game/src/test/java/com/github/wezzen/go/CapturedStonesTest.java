package com.github.wezzen.go;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CapturedStonesTest {

    @Test
    void addStonesTest() {
        final CapturedStones capturedStones = new CapturedStones();
        capturedStones.addCapturedStone(Mockito.mock(FieldPosition.class));
        assertEquals(1, capturedStones.getNumCapturedStones());
    }

    @Test
    void addSomeStonesTest() {
        final CapturedStones capturedStones = new CapturedStones();
        final int num = 10;
        for (int i = 0; i < num; i++) {
            capturedStones.addCapturedStone(Mockito.mock(FieldPosition.class));
        }
        assertEquals(num, capturedStones.getNumCapturedStones());
    }

    @Test
    void addStonesListTest() {
        final CapturedStones capturedStones = new CapturedStones();
        final int num = 10;
        final List<FieldPosition> stones = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            stones.add(Mockito.mock(FieldPosition.class));
        }
        capturedStones.addCapturedStone(stones);
        assertEquals(num, capturedStones.getNumCapturedStones());
    }
}