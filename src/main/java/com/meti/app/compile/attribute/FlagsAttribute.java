package com.meti.app.compile.attribute;

import com.meti.api.collect.java.JavaList;
import com.meti.api.collect.stream.Stream;
import com.meti.app.compile.common.Field;

public record FlagsAttribute(JavaList<Field.Flag> flags) implements Attribute {
    @Override
    public Stream<Field.Flag> asStreamOfFlags1() throws AttributeException {
        return flags.stream();
    }
}
