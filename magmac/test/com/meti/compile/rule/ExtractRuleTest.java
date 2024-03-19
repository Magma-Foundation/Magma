package com.meti.compile.rule;

import com.meti.collect.option.IntentionalException;
import com.meti.java.JavaString;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExtractRuleTest {

    @Test
    void apply() throws IntentionalException {
        var actual = ExtractRule.Extract("test")
                .apply(new JavaString("temp"))
                .$()
                .findText("test")
                .$();
        assertEquals(new JavaString("temp"), actual);
    }
}