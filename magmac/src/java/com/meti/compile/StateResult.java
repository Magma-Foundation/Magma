package com.meti.compile;

import com.meti.collect.JavaString;

import java.util.Optional;

public interface StateResult {
    Optional<JavaString> findInstanceValue();

    Optional<JavaString> findStaticValue();
}
