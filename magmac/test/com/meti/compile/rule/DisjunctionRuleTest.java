package com.meti.compile.rule;

import com.meti.java.JavaString;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DisjunctionRuleTest {

    @Test
    void apply() {
        assertTrue(DisjunctionRule.Or(
                Match.Match("test"),
                ExtractSymbolRule.Symbol("value")
        ).apply(JavaString.from("test")).isPresent());
    }
}