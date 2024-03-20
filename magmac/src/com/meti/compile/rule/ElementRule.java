package com.meti.compile.rule;

import com.meti.collect.JavaList;
import com.meti.collect.JavaMap;
import com.meti.collect.option.Option;
import com.meti.collect.option.Some;
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
    public Option<RuleResult> apply(JavaString input) {
        return Some.Some(new MapRuleResult(new JavaMap<JavaString, JavaList<JavaString>>()
                .put(name, new JavaList<>(List.of(input)))));
    }
}
