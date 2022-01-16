package com.meti.compile.attribute;

import com.meti.compile.common.EmptyField;

import java.util.Set;
import java.util.stream.Stream;

public record FlagsAttribute(Set<EmptyField.Flag> flags) implements Attribute {
    @Override
    public Stream<EmptyField.Flag> asStreamOfFlags() {
        return flags.stream();
    }
}
