package com.meti.compile.rule;

import com.meti.java.JavaString;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TextRuleTest {

    @Test
    void applyValid() {
        Assertions.assertTrue(TextRule.Text("test")
                .apply(JavaString.from("test"))
                .isPresent());
    }

    @Test
    void applyInvalid() {
        Assertions.assertTrue(TextRule.Text("test")
                .apply(JavaString.from("testing"))
                .isEmpty());
    }
}