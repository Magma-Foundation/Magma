package com.meti.compile.rule;

import com.meti.collect.option.None;
import com.meti.collect.option.Option;
import com.meti.collect.option.Some;
import com.meti.collect.stream.Streams;
import com.meti.java.JavaString;

public class EmptyRule implements Rule {
    public static final Rule Empty = new EmptyRule();

    @Override
    public Option<RuleResult> apply(JavaString input) {
        return input.isEmpty() ? Some.Some(new MapRuleResult(input.end())) : None.None();
    }
}
