package com.meti.compile.rule;

import com.meti.collect.option.IntentionalException;
import com.meti.java.JavaString;
import org.junit.jupiter.api.Test;

import static com.meti.compile.rule.ExtractSymbolRule.Symbol;
import static com.meti.compile.rule.TextRule.Text;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ConjunctionRuleTest {

    @Test
    void apply() throws IntentionalException {
        var actual = ConjunctionRule.Join(
                Text("a"),
                Symbol("value")
        ).apply(JavaString.from("ab")).$();

        var value = actual.findText("value").$().inner();
        assertEquals("b", value);
    }
}