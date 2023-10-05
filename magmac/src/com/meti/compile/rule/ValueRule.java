package com.meti.compile.rule;

import com.meti.api.collect.JavaString;
import com.meti.api.collect.map.ImmutableMaps;
import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;

public record ValueRule(JavaString value) implements Rule {
    public static Rule of(JavaString value) {
        return new ValueRule(value);
    }

    @Override
    public Option<RuleResult> extract(JavaString value) {
        if (!this.value.equals(value)) return None.apply();
        return Some.apply(new MapRuleResult(ImmutableMaps.empty(), ImmutableMaps.empty(), ImmutableMaps.empty()));
    }
}