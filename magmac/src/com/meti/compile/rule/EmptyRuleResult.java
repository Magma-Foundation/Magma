package com.meti.compile.rule;

import com.meti.collect.option.None;
import com.meti.collect.option.Option;
import com.meti.java.JavaString;

public class EmptyRuleResult implements RuleResult {
    public static RuleResult Empty = new EmptyRuleResult();

    @Override
    public Option<JavaString> text(String name) {
        return None.None();
    }
}
