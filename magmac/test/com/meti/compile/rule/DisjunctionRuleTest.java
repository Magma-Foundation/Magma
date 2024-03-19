package com.meti.compile.rule;

import com.meti.java.JavaString;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DisjunctionRuleTest {

    @Test
    void apply() {
        assertTrue(DisjunctionRule.Or(
                TextRule.Text("test"),
                ExtractSymbolRule.Symbol("value")
        ).apply(new JavaString("test")).isPresent());
    }
}