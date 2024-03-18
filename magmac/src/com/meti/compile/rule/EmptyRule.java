package com.meti.compile.rule;

import com.meti.collect.option.None;
import com.meti.collect.option.Some;
import com.meti.collect.stream.Stream;
import com.meti.java.JavaString;

public class EmptyRule implements Rule {
    public static final Rule Empty = new EmptyRule();

    @Override
    public Stream<RuleResult> apply(JavaString input) {
        return input.isEmpty() ? Some.Some(new MapRuleResult()) : None.None();
    }
}
