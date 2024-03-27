package com.meti.rule;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LazyRuleTest {
    @Test
    void test() {
        var rule = new LazyRule();
        rule.set(rule);

        var empty = rule.lexImpl("").isEmpty();
        assertTrue(empty);
    }
}