package com.meti.compile.rule;

import com.meti.collect.option.IntentionalException;
import com.meti.java.JavaString;
import org.junit.jupiter.api.Test;

import static com.meti.compile.rule.ConjunctionRule.Join;
import static com.meti.compile.rule.ExtractRule.Extract;
import static com.meti.compile.rule.TextRule.Text;
import static com.meti.compile.rule.WhitespaceRule.Whitespace;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WhitespaceRuleTest {
    @Test
    void apply1() throws IntentionalException {
        assertEquals(new JavaString("Test"), Join(Text("extends"), Whitespace, Extract("superclass"))
                .apply(new JavaString("extends Test"))
                .next()
                .$()
                .findText("superclass")
                .$());
    }

    @Test
    void apply() {
        assertTrue(Join(Text("extends"), Whitespace)
                .apply(new JavaString("extends \t"))
                .next()
                .isPresent());
    }
}