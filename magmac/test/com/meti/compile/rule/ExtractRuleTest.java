package com.meti.compile.rule;

import com.meti.collect.option.IntentionalException;
import com.meti.java.JavaString;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExtractRuleTest {

    @Test
    void apply() throws IntentionalException {
        var actual = ExtractSymbolRule.Symbol("test")
                .apply(JavaString.from("temp"))
                .$()
                .findText("test")
                .$();
        assertEquals(JavaString.from("temp"), actual);
    }
}