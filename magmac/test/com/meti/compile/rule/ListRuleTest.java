package com.meti.compile.rule;

import com.meti.collect.JavaList;
import com.meti.collect.option.IntentionalException;
import com.meti.collect.stream.Collectors;
import com.meti.collect.stream.Streams;
import com.meti.java.JavaString;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ListRuleTest {

    @Test
    void applyEmpty() {
        var actual = Rules.TextList("values", ";")
                .apply(JavaString.Empty)
                .collect(Collectors.toList());
        assertTrue(actual.isEmpty());
    }

    @Test
    void applyOnce() {
        var value = new JavaString("test");
        var actual = Rules.TextList("values", ";")
                .apply(value)
                .map(result -> result.findTextList("values"))
                .flatMap(Streams::fromOption)
                .flatMap(JavaList::stream)
                .collect(Collectors.toNativeList());

        assertIterableEquals(Collections.singletonList(value), actual);
    }

    @Test
    void applyMultiple() throws IntentionalException {
        var value = new JavaString("first;second");

        var actual = Rules.TextList("values", ";")
                .apply(value)
                .map(result -> result.findTextList("values"))
                .collect(Collectors.toNativeList())
                .get(0)
                .$()
                .unwrap();

        assertIterableEquals(
                List.of(new JavaString("first"),
                        new JavaString("second")), actual);
    }
}