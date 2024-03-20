package com.meti.compile.rule;

import com.meti.collect.JavaList;
import com.meti.collect.JavaMap;
import com.meti.collect.option.Option;
import com.meti.java.JavaString;

import java.util.List;

import static com.meti.collect.option.None.None;
import static com.meti.collect.option.Some.Some;

public class ExtractRule implements Rule {
    private final JavaString name;

    public ExtractRule(String name) {
        this.name = JavaString.from(name);
    }

    public static ExtractRule Extract(String name) {
        return new ExtractRule(name);
    }

    @Override
    public Option<RuleResult> apply(JavaString input) {
        return !input.isBlank()
                ? Some(new MapRuleResult(new JavaMap<JavaString, JavaList<JavaString>>().put(name, new JavaList<>(List.of(input)))))
                : None();
    }
}
