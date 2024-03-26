package com.meti.rule;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ListDelimitingRuleTest {
    @Test
    void empty() {
        var apply = new ListDelimitingRule(
                new RequireRule("final"),
                new RequireRule(" ")
        ).apply("").isPresent();
        Assertions.assertTrue(apply);
    }

    @Test
    void once() {
        var apply = new ListDelimitingRule(
                new RequireRule("final"),
                new RequireRule(" ")
        ).apply("final").isPresent();
        Assertions.assertTrue(apply);
    }

    @Test
    void twice() {
        var apply = new ListDelimitingRule(
                new RequireRule("final"),
                new RequireRule(" ")
        ).apply("final final").isPresent();
        Assertions.assertTrue(apply);
    }
}