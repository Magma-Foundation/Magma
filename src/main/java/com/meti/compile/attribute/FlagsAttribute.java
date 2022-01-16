package com.meti.compile.attribute;

import com.meti.collect.JavaList;
import com.meti.compile.common.EmptyField;
import com.meti.compile.common.Field;
import com.meti.compile.node.Node;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public record FlagsAttribute(Set<EmptyField.Flag> flags) implements Attribute {
    @Override
    public Stream<EmptyField.Flag> asStreamOfFlags() {
        return flags.stream();
    }

    @Override
    public com.meti.collect.Stream<Field.Flag> asStreamOfFlags1() throws AttributeException {
        return new JavaList<>(asStreamOfFlags().collect(Collectors.toList())).stream();
    }

    @Override
    public com.meti.collect.Stream<Node> asStreamOfNodes1() throws AttributeException {
        return new JavaList<>(asStreamOfNodes().collect(Collectors.toList())).stream();
    }
}
