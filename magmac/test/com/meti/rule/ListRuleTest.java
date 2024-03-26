package com.meti.rule;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ListRuleTest {
    @Test
    void empty() {
        var present = new ListRule(new RequireRule("test"))
                .apply("")
                .isPresent();
        assertTrue(present);
    }

    @Test
    void once() {
        var present = new ListRule(new RequireRule("test"))
                .apply("test")
                .isPresent();
        assertTrue(present);
    }

    @Test
    void twice() {
        var present = new ListRule(new RequireRule("00"))
                .apply("0000")
                .isPresent();
        assertTrue(present);
    }

    @Test
    void thrice() {
        var present = new ListRule(new RequireRule("000000"))
                .apply("000000")
                .isPresent();
        assertTrue(present);
    }

    @Test
    void invalid() {
        assertFalse(new ListRule(new RequireRule("0"))
                .apply("test")
                .isPresent());
    }
}