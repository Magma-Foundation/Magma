package com.meti.compile;

import com.meti.collect.JavaString;
import com.meti.option.Option;

import java.util.Optional;

public interface StateResult {
    Option<JavaString> findInstanceValue();

    Option<JavaString> findStaticValue();
}
