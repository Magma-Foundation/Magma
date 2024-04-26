package com.meti.compile;

import com.meti.collect.JavaString;
import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

import java.util.Optional;

public record InstanceResult(JavaString value) implements StateResult {
    @Override
    public Option<JavaString> findInstanceValue() {
        return new Some<>(value);
    }

    @Override
    public Option<JavaString> findStaticValue() {
        return new None<>();
    }
}
