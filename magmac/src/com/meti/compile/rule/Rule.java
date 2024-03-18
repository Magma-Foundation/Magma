package com.meti.compile.rule;

import com.meti.collect.stream.Stream;
import com.meti.java.JavaString;

public interface Rule {
    Stream<RuleResult> apply(JavaString input);
}
