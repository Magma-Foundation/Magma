package com.meti.compile.rule;

import com.meti.api.collect.JavaString;
import com.meti.api.collect.map.ImmutableMaps;
import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;

public record ContentRule(JavaString type) implements Rule {
    public static Rule of(JavaString type) {
        return new ContentRule(type);
    }

    @Override
    public Option<RuleResult> extract(JavaString value) {
        if(value.isBlank()) {
            return None.apply();
        }

        return Some.apply(new MapRuleResult(
                ImmutableMaps.empty(),
                ImmutableMaps.<String, JavaString>empty().put(type.value(), value),
                ImmutableMaps.empty()
        ));
    }
}
