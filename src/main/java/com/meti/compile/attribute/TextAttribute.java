package com.meti.compile.attribute;

import com.meti.collect.JavaList;
import com.meti.compile.common.EmptyField;
import com.meti.compile.common.Field;
import com.meti.compile.node.Node;
import com.meti.compile.node.Text;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public record TextAttribute(Text value) implements Attribute {
    @Override
    @Deprecated
    public Stream<EmptyField.Flag> asStreamOfFlags() throws AttributeException {
        throw new AttributeException("Not a ist of flags.");
    }

    @Override
    public com.meti.collect.Stream<Field.Flag> asStreamOfFlags1() throws AttributeException {
        return new JavaList<>(asStreamOfFlags().collect(Collectors.toList())).stream();
    }

    @Override
    public com.meti.collect.Stream<Node> asStreamOfNodes1() throws AttributeException {
        return new JavaList<>(asStreamOfNodes().collect(Collectors.toList())).stream();
    }

    @Override
    public Text asText() {
        return value;
    }
}
