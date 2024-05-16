package com.meti.node;

import com.meti.api.option.Option;
import com.meti.api.option.Options;
import com.meti.compile.JavaString;

import java.util.Optional;

public record StringAttribute(String value) implements Attribute {
    private Optional<String> asString1() {
        return Optional.of(value);
    }

    @Override
    public Option<JavaString> asString() {
        return Options.fromNative(asString1().map(JavaString::new));
    }
}
