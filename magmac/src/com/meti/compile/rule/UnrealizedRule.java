package com.meti.compile.rule;

import com.meti.api.collect.JavaString;
import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;

public record UnrealizedRule(JavaString value) implements Rule {
    @Override
    public Option<RuleResult> extract(JavaString value) {
        return None.apply();
    }

    @Override
    public Option<JavaString> asString() {
        return Some.apply(value);
    }
}
