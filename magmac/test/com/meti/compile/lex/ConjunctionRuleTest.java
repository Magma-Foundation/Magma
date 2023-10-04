package com.meti.compile.lex;

import com.meti.api.collect.JavaString;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ConjunctionRuleTest {
    @Test
    void invalidOrder() {
        Assertions.assertTrue(new ConjunctionRule(
                new ValueRule(JavaString.apply("b")),
                new ValueRule(JavaString.apply("a"))
        ).extract(JavaString.apply("ab")).isEmpty());
    }

    @Test
    void invalidRight() {
        Assertions.assertTrue(new ConjunctionRule(
                new ValueRule(JavaString.apply("a")),
                new ValueRule(JavaString.apply("c"))
        ).extract(JavaString.apply("ab")).isEmpty());
    }

    @Test
    void invalidLeft() {
        Assertions.assertTrue(new ConjunctionRule(
                new ValueRule(JavaString.apply("c")),
                new ValueRule(JavaString.apply("b"))
        ).extract(JavaString.apply("ab")).isEmpty());
    }

    @Test
    void extractValid() {
        Assertions.assertTrue(new ConjunctionRule(
                new ValueRule(JavaString.apply("a")),
                new ValueRule(JavaString.apply("b"))
        ).extract(JavaString.apply("ab")).isPresent());
    }
}