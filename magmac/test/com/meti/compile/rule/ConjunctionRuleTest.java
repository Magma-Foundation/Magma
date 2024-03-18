package com.meti.compile.rule;

import com.meti.collect.option.IntentionalException;
import com.meti.java.JavaString;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConjunctionRuleTest {

    @Test
    void apply() throws IntentionalException {
        var actual = ConjunctionRule.Join(
                TextRule.Text("a"),
                ExtractRule.Extract("value")
        ).apply(new JavaString("ab")).next().$();

        var value = actual.findText("value").$().inner();
        assertEquals("b", value);
    }
}