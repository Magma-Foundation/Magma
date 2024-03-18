package com.meti.compile.rule;

import com.meti.collect.JavaMap;
import com.meti.java.JavaString;

public class ValueRule implements Rule {
    private final JavaString name;

    public ValueRule(String name) {
        this.name = new JavaString(name);
    }

    @Override
    public RuleResult apply(JavaString input) {
        return new MapRuleResult(new JavaMap<JavaString, JavaString>()
                .put(name, input), new JavaMap<>());
    }
}
