package com.meti.compile.rule;

import com.meti.collect.JavaMap;
import com.meti.collect.option.Some;
import com.meti.collect.stream.Stream;
import com.meti.java.JavaString;

public class ExtractRule implements Rule {
    private final JavaString name;

    private ExtractRule(String name) {
        this.name = new JavaString(name);
    }

    public static ExtractRule Extract(String name) {
        return new ExtractRule(name);
    }

    @Override
    public Stream<RuleResult> apply(JavaString input) {
        return Some.Some(new MapRuleResult(new JavaMap<JavaString, JavaString>()
                .put(name, input), new JavaMap<>()));
    }
}
