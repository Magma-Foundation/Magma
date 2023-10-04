package com.meti.compile.lex;

import com.meti.api.collect.JavaString;
import com.meti.api.collect.map.ImmutableMaps;
import com.meti.api.option.Option;
import com.meti.api.option.Some;

public record AnyRule(JavaString type) implements Rule {
    @Override
    public Option<RuleResult> extract(JavaString value) {
        return Some.apply(new MapRuleResult(
                ImmutableMaps.<String, JavaString>empty().put(type.value(), value),
                ImmutableMaps.empty()
        ));
    }
}
