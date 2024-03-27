package com.meti.rule;

import org.junit.jupiter.api.Test;

import java.util.Stack;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ListRuleTest {
    private final Stack<String> stack = new Stack<>();

    @Test
    void empty() {
        var present = ((Rule) new ListRule(new RequireRule("test"))).lexImpl("").map(tuple -> tuple.b())
                .isPresent();
        assertTrue(present);
    }

    @Test
    void once() {
        var present = ((Rule) new ListRule(new RequireRule("test"))).lexImpl("test").map(tuple -> tuple.b())
                .isPresent();
        assertTrue(present);
    }

    @Test
    void twice() {
        var present = ((Rule) new ListRule(new RequireRule("00"))).lexImpl("0000").map(tuple -> tuple.b())
                .isPresent();
        assertTrue(present);
    }

    @Test
    void thrice() {
        var present = ((Rule) new ListRule(new RequireRule("000000"))).lexImpl("000000").map(tuple -> tuple.b())
                .isPresent();
        assertTrue(present);
    }

    @Test
    void invalid() {
        assertFalse(((Rule) new ListRule(new RequireRule("0"))).lexImpl("test").map(tuple -> tuple.b())
                .isPresent());
    }
}