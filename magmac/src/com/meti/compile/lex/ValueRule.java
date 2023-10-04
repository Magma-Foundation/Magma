package com.meti.compile.lex;

import com.meti.api.collect.JavaString;
import com.meti.api.collect.map.ImmutableMaps;
import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;

public record ValueRule(JavaString value) implements Rule {
    @Override
    public Option<RuleResult> extract(JavaString value) {
        if (!this.value.equals(value)) return None.apply();
        return Some.apply(new MapRuleResult(ImmutableMaps.empty(), ImmutableMaps.empty()));
    }
}
