package com.meti.rule;

import org.junit.jupiter.api.Test;

import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

class RangeRuleTest {
    private static final Stack<String> stack = new Stack<>();

    @Test
    void simple() {
        assertTrue(((Rule) new RangeRule('a', 'z')).lexImpl("b").map(tuple -> tuple.b())
                .isPresent());
    }

    @Test
    void left() {
        assertTrue(((Rule) new RangeRule('a', 'z')).lexImpl("a").map(tuple -> tuple.b())
                .isPresent());
    }

    @Test
    void right() {
        assertTrue(((Rule) new RangeRule('a', 'z')).lexImpl("z").map(tuple -> tuple.b())
                .isPresent());
    }

    @Test
    void outsideLeft() {
        assertTrue(((Rule) new RangeRule('4', '8')).lexImpl("3").map(tuple -> tuple.b())
                .isEmpty());
    }

    @Test
    void outsideRight() {
        assertTrue(((Rule) new RangeRule('4', '8')).lexImpl("9").map(tuple -> tuple.b())
                .isEmpty());
    }
}