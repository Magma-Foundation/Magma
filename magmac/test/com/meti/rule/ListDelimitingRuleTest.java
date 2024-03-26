package com.meti.rule;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ListDelimitingRuleTest {
    @Test
    void empty() {
        var apply = new ListDelimitingRule(
                new RequireRule(" "), new RequireRule("final")
        ).apply("").isPresent();
        Assertions.assertTrue(apply);
    }

    @Test
    void once() {
        var apply = new ListDelimitingRule(
                new RequireRule(" "), new RequireRule("final")
        ).apply("final").isPresent();
        Assertions.assertTrue(apply);
    }

    @Test
    void twice() {
        var apply = new ListDelimitingRule(
                new RequireRule(" "), new RequireRule("final")
        ).apply("final final").isPresent();
        Assertions.assertTrue(apply);
    }
}