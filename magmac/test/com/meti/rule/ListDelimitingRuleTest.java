package com.meti.rule;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ListDelimitingRuleTest {
    @Test
    void empty() {
        Rule rule = new ListDelimitingRule(
                new RequireRule(" "), new RequireRule("final")
        );
        var apply = rule.apply("").map(tuple -> tuple.b()).isPresent();
        Assertions.assertTrue(apply);
    }

    @Test
    void once() {
        Rule rule = new ListDelimitingRule(
                new RequireRule(" "), new RequireRule("final")
        );
        var apply = rule.apply("final").map(tuple -> tuple.b()).isPresent();
        Assertions.assertTrue(apply);
    }

    @Test
    void twice() {
        Rule rule = new ListDelimitingRule(
                new RequireRule(" "), new RequireRule("final")
        );
        var apply = rule.apply("final final").map(tuple -> tuple.b()).isPresent();
        Assertions.assertTrue(apply);
    }
}