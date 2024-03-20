package com.meti.compile.rule;

import com.meti.collect.option.None;
import com.meti.collect.option.Option;
import com.meti.collect.option.Some;
import com.meti.java.JavaString;

public class Match implements Rule {
    private final JavaString requiredValue;

    private Match(JavaString requiredValue) {
        this.requiredValue = requiredValue;
    }

    public static Match Match(String requiredValue) {
        return new Match(JavaString.from(requiredValue));
    }

    @Override
    public String toString() {
        return "\"" + requiredValue + "\"";
    }

    @Override
    public Option<RuleResult> apply(JavaString input) {
        return input.equals(requiredValue) ? Some.Some(new MapRuleResult()) : None.None();
    }
}
