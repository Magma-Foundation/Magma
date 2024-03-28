package com.meti.rule;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RangeRuleTest {

    @Test
    void simple() {
        assertTrue(new RangeRule('a', 'z').lex("b").map(tuple -> tuple.b())
                .isPresent());
    }

    @Test
    void left() {
        assertTrue(new RangeRule('a', 'z').lex("a").map(tuple -> tuple.b())
                .isPresent());
    }

    @Test
    void right() {
        assertTrue(new RangeRule('a', 'z').lex("z").map(tuple -> tuple.b())
                .isPresent());
    }

    @Test
    void outsideLeft() {
        assertTrue(new RangeRule('4', '8').lex("3").map(tuple -> tuple.b())
                .isEmpty());
    }

    @Test
    void outsideRight() {
        assertTrue(new RangeRule('4', '8').lex("9").map(tuple -> tuple.b())
                .isEmpty());
    }
}