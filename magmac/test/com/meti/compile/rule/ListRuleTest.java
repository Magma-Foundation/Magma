package com.meti.compile.rule;

import com.meti.collect.Range;
import com.meti.collect.option.IntentionalException;
import com.meti.java.JavaString;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static com.meti.compile.rule.TextRule.Text;
import static com.meti.java.JavaString.from;
import static org.junit.jupiter.api.Assertions.*;

class ListRuleTest {

    @Test
    void applyEmpty() {
        var actual = Rules.SymbolList("values", ";").apply(JavaString.Empty);
        assertTrue(actual.isEmpty());
    }

    @Test
    void applyOnce() throws IntentionalException {
        var value = from("test");
        var actual = Rules.SymbolList("values", ";")
                .apply(value)
                .flatMap(result -> result.findTextList("values"))
                .$().unwrap();

        assertIterableEquals(Collections.singletonList(value), actual);
    }

    @Test
    void applyOnceAndFindFirstInstanceDelimiter() {
        assertTrue(ListRule.findFirstInstance(from("test"), Text(";"))
                .isEmpty());
    }

    @Test
    void applyMultiple() throws IntentionalException {
        var value = from("first;second");

        var actual = Rules.SymbolList("values", ";")
                .apply(value)
                .flatMap(result -> result.findTextList("values"))
                .$()
                .unwrap();

        assertIterableEquals(
                List.of(from("first"),
                        from("second")), actual);
    }

    @Test
    void findFirstInstance() throws IntentionalException {
        var unwrap = ListRule.findFirstInstance(
                from("test"),
                Text("es")
        ).$();

        assertEquals(new Range(1, 3, 4), unwrap);
    }


}