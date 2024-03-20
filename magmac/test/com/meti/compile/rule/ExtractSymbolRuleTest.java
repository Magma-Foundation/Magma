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
                .apply(JavaString.from(""))
                .isEmpty());
    }

    @Test
    void badChar() {
        assertTrue(new ExtractSymbolRule("test")
                .apply(JavaString.from(";"))
                .isEmpty());
    }

    @Test
    void valid() throws IntentionalException {
        var actual = new ExtractSymbolRule("test")
                .apply(JavaString.from("test123"))
                .$()
                .findTextList("test")
                .$()
                .unwrap()
                .get(0);

        assertEquals(JavaString.from("test123"), actual);
    }
}