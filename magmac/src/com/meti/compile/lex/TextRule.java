package com.meti.compile.lex;

import com.meti.api.collect.JavaString;
import com.meti.api.collect.map.ImmutableMaps;
import com.meti.api.option.Option;
import com.meti.api.option.Some;

public record TextRule(JavaString type) implements Rule {
    public static Rule of(JavaString type) {
        return new TextRule(type);
    }

    @Override
    public Option<RuleResult> extract(JavaString value) {
        return Some.apply(new MapRuleResult(
                ImmutableMaps.<String, JavaString>empty().put(type.value(), value),
                ImmutableMaps.empty(), ImmutableMaps.empty()
        ));
    }
}
