package com.meti.rule;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Stack;

class ListDelimitingRuleTest {

    private static final Stack<String> stack = new Stack<>();

    @Test
    void empty() {
        Rule rule = new ListDelimitingRule(
                new RequireRule(" "), new RequireRule("final")
        );
        var apply = rule.lex("", stack).map(tuple -> tuple.b()).isPresent();
        Assertions.assertTrue(apply);
    }

    @Test
    void once() {
        Rule rule = new ListDelimitingRule(
                new RequireRule(" "), new RequireRule("final")
        );
        var apply = rule.lex("final", stack).map(tuple -> tuple.b()).isPresent();
        Assertions.assertTrue(apply);
    }

    @Test
    void twice() {
        Rule rule = new ListDelimitingRule(
                new RequireRule(" "), new RequireRule("final")
        );
        var apply = rule.lex("final final", stack).map(tuple -> tuple.b()).isPresent();
        Assertions.assertTrue(apply);
    }
}