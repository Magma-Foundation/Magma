package com.meti.compile.rule;

import com.meti.collect.option.IntentionalException;
import com.meti.java.JavaString;
import org.junit.jupiter.api.Test;

import static com.meti.compile.rule.ConjunctionRule.Join;
import static com.meti.compile.rule.Match.Match;
import static com.meti.compile.rule.WhitespaceRule.Whitespace;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WhitespaceRuleTest {
    @Test
    void joinBetween() throws IntentionalException {
        assertEquals(JavaString.from("Test"), Join(Match("extends"), Whitespace, ExtractSymbolRule.Symbol("superclass"))
                .apply(JavaString.from("extends Test"))
                .$()
                .findText("superclass")
                .$());
    }

    @Test
    void joinWithText() {
        assertTrue(Join(Match("extends"), Whitespace)
                .apply(JavaString.from("extends \t"))
                .isPresent());
    }
}