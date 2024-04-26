package com.meti.compile;

import com.meti.collect.JavaString;
import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

import java.util.Optional;

public record StaticResult(JavaString value) implements StateResult {
    @Override
    public Option<JavaString> findInstanceValue() {
        return new None<>();
    }

    @Override
    public Option<JavaString> findStaticValue() {
        return new Some<>(value);
    }
}
