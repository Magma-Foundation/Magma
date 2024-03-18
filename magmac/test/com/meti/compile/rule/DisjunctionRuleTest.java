package com.meti.compile.rule;

import com.meti.collect.JavaList;
import com.meti.collect.stream.Collectors;
import com.meti.java.JavaString;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class DisjunctionRuleTest {

    @Test
    void apply() {
        var list = DisjunctionRule.Or(
                TextRule.Text("test"),
                ExtractRule.Extract("value")
        ).apply(new JavaString("test")).collect(Collectors.toList());
        assertEquals(2, list.size());
    }
}