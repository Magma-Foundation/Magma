package com.meti.compile.rule;

import com.meti.collect.stream.Stream;
import com.meti.collect.stream.Streams;
import com.meti.java.JavaString;

public class WhitespaceRule implements Rule {
    public static final Rule Whitespace = new WhitespaceRule();
    public static final Rule Padding = Rules.Optional(Whitespace);

    @Override
    public Stream<RuleResult> apply(JavaString input) {
        return input.isBlank() ? Streams.from(new MapRuleResult()) : Streams.empty();
    }
}
