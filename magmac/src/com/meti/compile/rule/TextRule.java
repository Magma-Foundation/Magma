package com.meti.compile.rule;

import com.meti.collect.option.None;
import com.meti.collect.option.Option;
import com.meti.collect.option.Some;
import com.meti.collect.stream.Streams;
import com.meti.java.JavaString;

public class TextRule implements Rule {
    private final JavaString requiredValue;

    private TextRule(JavaString requiredValue) {
        this.requiredValue = requiredValue;
    }

    public static TextRule Text(String requiredValue) {
        return new TextRule(new JavaString(requiredValue));
    }

    @Override
    public Option<RuleResult> apply(JavaString input) {
        return input.equals(requiredValue) ? Some.Some(new MapRuleResult()) : None.None();
    }
}
