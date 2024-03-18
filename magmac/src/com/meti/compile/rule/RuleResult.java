package com.meti.compile.rule;

import com.meti.collect.option.Option;
import com.meti.java.JavaString;

public interface RuleResult {
    Option<JavaString> text(String name);
}
