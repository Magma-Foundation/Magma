package com.meti.rule;

import org.junit.jupiter.api.Test;

import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

class RangeRuleTest {
    private static final Stack<String> stack = new Stack<>();

    @Test
    void simple() {
        assertTrue(((Rule) new RangeRule('a', 'z')).lex("b", stack).map(tuple -> tuple.b())
                .isPresent());
    }

    @Test
    void left() {
        assertTrue(((Rule) new RangeRule('a', 'z')).lex("a", stack).map(tuple -> tuple.b())
                .isPresent());
    }

    @Test
    void right() {
        assertTrue(((Rule) new RangeRule('a', 'z')).lex("z", stack).map(tuple -> tuple.b())
                .isPresent());
    }

    @Test
    void outsideLeft() {
        assertTrue(((Rule) new RangeRule('4', '8')).lex("3", stack).map(tuple -> tuple.b())
                .isEmpty());
    }

    @Test
    void outsideRight() {
        assertTrue(((Rule) new RangeRule('4', '8')).lex("9", stack).map(tuple -> tuple.b())
                .isEmpty());
    }
}