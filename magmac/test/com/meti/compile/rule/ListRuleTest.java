package com.meti.compile.rule;

import com.meti.collect.Range;
import com.meti.collect.option.IntentionalException;
import com.meti.java.JavaString;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ListRuleTest {

    @Test
    void applyEmpty() {
        var actual = Rules.SymbolList("values", ";").apply(JavaString.Empty);
        assertTrue(actual.isEmpty());
    }

    @Test
    void applyOnce() throws IntentionalException {
        var value = new JavaString("test");
        var actual = Rules.SymbolList("values", ";")
                .apply(value)
                .flatMap(result -> result.findTextList("values"))
                .$().unwrap();

        assertIterableEquals(Collections.singletonList(value), actual);
    }

    @Test
    void applyMultiple() throws IntentionalException {
        var value = new JavaString("first;second");

        var actual = Rules.SymbolList("values", ";")
                .apply(value)
                .flatMap(result -> result.findTextList("values"))
                .$()
                .unwrap();

        assertIterableEquals(
                List.of(new JavaString("first"),
                        new JavaString("second")), actual);
    }

    @Test
    void findFirstInstance() throws IntentionalException {
        var unwrap = ListRule.findFirstInstance(
                new JavaString("test"),
                TextRule.Text("es")
        ).$();

        assertEquals(new Range(1, 3, 4), unwrap);
    }


}