package com.meti.compile.rule;

import com.meti.collect.option.IntentionalException;
import com.meti.java.JavaString;
import org.junit.jupiter.api.Test;

import static com.meti.compile.rule.ConjunctionRule.Join;
import static com.meti.compile.rule.TextRule.Text;
import static com.meti.compile.rule.WhitespaceRule.Whitespace;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WhitespaceRuleTest {
    @Test
    void joinBetween() throws IntentionalException {
        assertEquals(new JavaString("Test"), Join(Text("extends"), Whitespace, ExtractSymbolRule.Symbol("superclass"))
                .apply(new JavaString("extends Test"))
                .$()
                .findText("superclass")
                .$());
    }

    @Test
    void joinWithText() {
        assertTrue(Join(Text("extends"), Whitespace)
                .apply(new JavaString("extends \t"))
                .isPresent());
    }
}