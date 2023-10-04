package com.meti.compile.lex;

import com.meti.api.collect.JavaString;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ConjunctionRuleTest {
    @Test
    void test() {
        var present = ConjunctionRule.of(
                AnyRule.of(JavaString.apply("type")),
                ValueRule.of(JavaString.apply(" "))
        ).extract(JavaString.apply("test ")).isPresent();
        assertTrue(present);
    }

    @Test
    void invalidOrder() {
        assertTrue(new ConjunctionRule(
                ValueRule.of(JavaString.apply("b")),
                ValueRule.of(JavaString.apply("a"))
        ).extract(JavaString.apply("ab")).isEmpty());
    }

    @Test
    void invalidRight() {
        assertTrue(new ConjunctionRule(
                ValueRule.of(JavaString.apply("a")),
                ValueRule.of(JavaString.apply("c"))
        ).extract(JavaString.apply("ab")).isEmpty());
    }

    @Test
    void invalidLeft() {
        assertTrue(new ConjunctionRule(
                ValueRule.of(JavaString.apply("c")),
                ValueRule.of(JavaString.apply("b"))
        ).extract(JavaString.apply("ab")).isEmpty());
    }

    @Test
    void extractValid() {
        assertTrue(new ConjunctionRule(
                ValueRule.of(JavaString.apply("a")),
                ValueRule.of(JavaString.apply("b"))
        ).extract(JavaString.apply("ab")).isPresent());
    }
}