package com.meti.rule;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ListRuleTest {
    @Test
    void empty() {
        var present = new ListRule(new RequireRule("test")).lex("").map(tuple -> tuple.b())
                .isPresent();
        assertTrue(present);
    }

    @Test
    void once() {
        var present = new ListRule(new RequireRule("test")).lex("test").map(tuple -> tuple.b())
                .isPresent();
        assertTrue(present);
    }

    @Test
    void twice() {
        var present = new ListRule(new RequireRule("00")).lex("0000").map(tuple -> tuple.b())
                .isPresent();
        assertTrue(present);
    }

    @Test
    void thrice() {
        var present = new ListRule(new RequireRule("000000")).lex("000000").map(tuple -> tuple.b())
                .isPresent();
        assertTrue(present);
    }

    @Test
    void invalid() {
        assertFalse(new ListRule(new RequireRule("0")).lex("test").map(tuple -> tuple.b())
                .isPresent());
    }
}