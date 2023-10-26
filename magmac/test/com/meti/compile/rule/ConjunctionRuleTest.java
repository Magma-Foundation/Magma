package com.meti.compile.rule;

import com.meti.compile.option.Option;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ConjunctionRuleTest {
    @Test
    void size() {
        var results = new ConjunctionRule(new ValueRule("left"), new ValueRule("right"))
                .evaluate("abc")
                .unwrapOrElse(Collections.emptyList());
        assertEquals(2, results.size());
    }

    @Test
    void first() {
        var results = new ConjunctionRule(new ValueRule("left"), new ValueRule("right"))
                .evaluate("abc")
                .unwrapOrElse(Collections.emptyList());
        var actual = results.get(0).values();
        assertEquals(Map.of("left", "a", "right", "bc"), actual);
    }

    @Test
    void second() {
        var results = new ConjunctionRule(new ValueRule("left"), new ValueRule("right"))
                .evaluate("abc")
                .unwrapOrElse(Collections.emptyList());
        var actual = results.get(1).values();
        assertEquals(Map.of("left", "ab", "right", "c"), actual);
    }
}