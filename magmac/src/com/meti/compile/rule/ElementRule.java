package com.meti.compile.rule;

import com.meti.collect.JavaList;
import com.meti.collect.JavaMap;
import com.meti.collect.stream.Stream;
import com.meti.collect.stream.Streams;
import com.meti.java.JavaString;

import java.util.List;

public class ElementRule implements Rule {
    private final JavaString name;

    private ElementRule(String name) {
        this.name = new JavaString(name);
    }

    public static ElementRule Element(String name) {
        return new ElementRule(name);
    }

    @Override
    public Stream<RuleResult> apply(JavaString input) {
        return Streams.from(new MapRuleResult(new JavaMap<>(), new JavaMap<JavaString, JavaList<JavaString>>()
                .put(name, new JavaList<>(List.of(input)))));
    }
}
