package com.meti.compile.rule;

import com.meti.collect.JavaList;
import com.meti.collect.JavaMap;
import com.meti.collect.stream.Collectors;
import com.meti.collect.stream.Stream;
import com.meti.java.JavaString;

public record TextSplitRule(String name, String regex) implements Rule {
    @Override
    public Stream<RuleResult> apply(JavaString input) {
        var split = input.split(regex).collect(Collectors.toList());
        return new MapRuleResult(new JavaMap<>(), new JavaMap<JavaString, JavaList<JavaString>>().put(new JavaString(name), split));
    }
}
