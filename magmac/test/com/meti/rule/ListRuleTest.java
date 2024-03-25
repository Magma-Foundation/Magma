package com.meti.rule;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ListRuleTest {
    @Test
    void empty(){
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
}