package com.meti.rule;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RangeRuleTest {

    @Test
    void simple() {
        assertTrue(new RangeRule('a', 'z')
                .apply("b")
                .isPresent());
    }

    @Test
    void left() {
        assertTrue(new RangeRule('a', 'z')
                .apply("a")
                .isPresent());
    }

    @Test
    void right() {
        assertTrue(new RangeRule('a', 'z')
                .apply("z")
                .isPresent());
    }

    @Test
    void outsideLeft() {
        assertTrue(new RangeRule('4', '8')
                .apply("3")
                .isEmpty());
    }

    @Test
    void outsideRight() {
        assertTrue(new RangeRule('4', '8')
                .apply("9")
                .isEmpty());
    }
}