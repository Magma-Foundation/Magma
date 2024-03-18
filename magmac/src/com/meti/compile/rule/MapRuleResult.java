package com.meti.compile.rule;

import com.meti.collect.JavaMap;
import com.meti.collect.option.Option;
import com.meti.java.JavaString;

public record MapRuleResult(JavaMap<JavaString, JavaString> map) implements RuleResult {
    @Override
    public Option<JavaString> text(String name) {
        return map.apply(new JavaString(name));
    }
}
