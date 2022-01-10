package com.meti.compile.attribute;

import com.meti.compile.common.Field;

import java.util.Set;
import java.util.stream.Stream;

public record FlagsAttribute(Set<Field.Flag> flags) implements Attribute {
    @Override
    public Stream<Field.Flag> asStreamOfFlags() {
        return flags.stream();
    }
}
