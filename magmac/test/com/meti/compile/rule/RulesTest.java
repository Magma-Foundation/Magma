package com.meti.compile.rule;

import com.meti.java.JavaString;
import org.junit.jupiter.api.Test;

import static com.meti.compile.rule.TextRule.Text;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RulesTest {
    @Test
    void presentInvalid() {
        assertFalse(Rules.Optional(Text("test"))
                .apply(new JavaString("tests"))
                .isPresent());
    }

    @Test
    void presentValid() {
        assertTrue(Rules.Optional(ExtractSymbolRule.Symbol("value"))
                .apply(new JavaString("test"))
                .isPresent());
    }

    @Test
    void empty() {
        assertTrue(Rules.Optional(ExtractSymbolRule.Symbol("value"))
                .apply(new JavaString(""))
                .isPresent());
    }
}