package com.meti.compile.rule;

import com.meti.api.collect.JavaString;
import com.meti.api.option.Option;

public interface Rule {
    Option<RuleResult> extract(JavaString value);
}
