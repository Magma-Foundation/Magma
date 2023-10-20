package com.meti.compile.rule;

import com.meti.api.collect.JavaString;
import com.meti.api.option.None;
import com.meti.api.option.Option;

public interface Rule {
    Option<RuleResult> extract(JavaString value);

    default Option<JavaString> asString() {
        return None.apply();
    }

    default Rule withLeft(Rule rule) {
        return this;
    }

    default Rule withRight(Rule rule) {
        return this;
    }

    default Option<Rule> left() {
        return None.apply();
    }

    default Option<Rule> right() {
        return None.apply();
    }
}
