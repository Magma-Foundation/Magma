package com.meti.compile.rule;

import com.meti.collect.option.IntentionalException;
import com.meti.java.JavaString;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ExtractSymbolRuleTest {
    @Test
    void empty() {
        assertTrue(new ExtractSymbolRule("test")
                .apply(new JavaString(""))
                .isEmpty());
    }

    @Test
    void badChar() {
        assertTrue(new ExtractSymbolRule("test")
                .apply(new JavaString(";"))
                .isEmpty());
    }

    @Test
    void valid() throws IntentionalException {
        var actual = new ExtractSymbolRule("test")
                .apply(new JavaString("test123"))
                .$()
                .findTextList("test")
                .$()
                .unwrap()
                .get(0);

        assertEquals(new JavaString("test123"), actual);
    }
}