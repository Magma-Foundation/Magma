package com.meti.compile.rule;

import com.meti.collect.option.None;
import com.meti.collect.option.Option;
import com.meti.collect.option.Some;
import com.meti.collect.stream.Streams;
import com.meti.java.JavaString;

public class WhitespaceRule implements Rule {
    public static final Rule Whitespace = new WhitespaceRule();
    public static final Rule Padding = Rules.Optional(Whitespace);

    @Override
    public Option<RuleResult> apply(JavaString input) {
        return input.isBlank() && !input.isEmpty() ? Some.Some(new MapRuleResult()) : None.None();
    }
}
