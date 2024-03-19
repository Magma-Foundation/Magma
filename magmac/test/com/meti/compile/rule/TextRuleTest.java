package com.meti.compile.rule;

import com.meti.collect.option.IntentionalException;
import com.meti.java.JavaString;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TextRuleTest {

    @Test
    void applyValid() {
        Assertions.assertTrue(TextRule.Text("test")
                .apply(new JavaString("test"))
                .isPresent());
    }

    @Test
    void applyInvalid() {
        Assertions.assertTrue(TextRule.Text("test")
                .apply(new JavaString("testing"))
                .isEmpty());
    }
}