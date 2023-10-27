package com.meti.compile.rule;

import com.meti.compile.collect.JavaList;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConjunctionRuleTest {
    @Test
    void size() {
        var results = new ConjunctionRule(new ValueRule("left"), new ValueRule("right"))
                .evaluate("abc")
                .unwrapOrElse(JavaList.empty());
        assertEquals(2, results.size());
    }

    @Test
    void first() {
        var results = new ConjunctionRule(new ValueRule("left"), new ValueRule("right"))
                .evaluate("abc")
                .unwrapOrElse(JavaList.empty());
        var actual = results.get(0).map(Rule.Result::values).unwrapOrElse(new HashMap<>());
        assertEquals(Map.of("left", "a", "right", "bc"), actual);
    }

    @Test
    void second() {
        var results = new ConjunctionRule(new ValueRule("left"), new ValueRule("right"))
                .evaluate("abc")
                .unwrapOrElse(JavaList.empty());
        var actual = results.get(1).map(Rule.Result::values).unwrapOrElse(new HashMap<>());
        assertEquals(Map.of("left", "ab", "right", "c"), actual);
    }
}