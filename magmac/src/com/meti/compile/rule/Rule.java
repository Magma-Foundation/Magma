package com.meti.compile.rule;

import com.meti.java.JavaString;

public interface Rule {
    RuleResult apply(JavaString input);
}
