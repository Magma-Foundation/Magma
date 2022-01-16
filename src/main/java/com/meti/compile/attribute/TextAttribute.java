package com.meti.compile.attribute;

import com.meti.collect.JavaList;
import com.meti.compile.node.Node;
import com.meti.compile.node.Text;

import java.util.stream.Collectors;

public record TextAttribute(Text value) implements Attribute {
    @Override
    public com.meti.collect.Stream<Node> asStreamOfNodes1() throws AttributeException {
        return new JavaList<>(asStreamOfNodes().collect(Collectors.toList())).stream();
    }

    @Override
    public Text asText() {
        return value;
    }
}
