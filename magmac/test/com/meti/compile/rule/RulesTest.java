package com.meti.compile.rule;

import com.meti.java.JavaString;
import org.junit.jupiter.api.Test;

import static com.meti.compile.rule.ExtractRule.Extract;
import static com.meti.compile.rule.TextRule.Text;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RulesTest {
    @Test
    void presentInvalid() {
        assertFalse(Rules.Optional(Text("test"))
                .apply(new JavaString("tests"))
                .next()
                .isPresent());
    }

    @Test
    void presentValid() {
        assertTrue(Rules.Optional(Extract("value"))
                .apply(new JavaString("test"))
                .next()
                .isPresent());
    }

    @Test
    void empty() {
        assertTrue(Rules.Optional(Extract("value"))
                .apply(new JavaString(""))
                .next()
                .isPresent());
    }
}