package com.meti.node;

import com.meti.api.option.Option;
import com.meti.api.option.Options;
import com.meti.compile.JavaString;

import java.util.Optional;

public record IntAttribute(int value) implements Attribute {
    @Override
    public Optional<Integer> asInt() {
        return Optional.of(value);
    }

    private Optional<String> asString1() {
        return Options.toNative(asString().map(JavaString::value));
    }

    @Override
    public Option<JavaString> asString() {
        return Options.fromNative(asString1().map(JavaString::new));
    }
}
