package com.github.wezzen.go;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FieldPositionTest {

    @Test
    void creatingTest() {
        final int x = 5;
        final int y = 5;
        final FieldPosition fieldPosition = new FieldPosition(x, y);
        assertEquals(x, fieldPosition.x);
        assertEquals(y, fieldPosition.y);
        assertNull(fieldPosition.getChain());
    }

    @Test
    void comparePositionsTest() {
        final FieldPosition fieldPosition = new FieldPosition(5, 5);
        assertNotEquals(fieldPosition, new Object());
        assertEquals(fieldPosition, fieldPosition);
    }

}