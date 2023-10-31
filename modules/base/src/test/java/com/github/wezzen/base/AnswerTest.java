package com.github.wezzen.base;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnswerTest {

    @Test
    void answerCreationTest() {
        final Answer.Type type = Answer.Type.ACTION;
        final Action action = new Action(0, 0);
        final Answer answer = new Answer(type, action);
        assertEquals(type, answer.type);
        assertEquals(action, answer.action);
    }


}