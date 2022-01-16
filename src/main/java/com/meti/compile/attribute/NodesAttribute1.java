package com.meti.compile.attribute;

import com.meti.collect.JavaList;
import com.meti.collect.StreamException;
import com.meti.compile.node.Node;

import java.util.stream.Stream;

public record NodesAttribute1(JavaList<Node> values) implements Attribute {
    @Override
    public com.meti.collect.Stream<Node> asStreamOfNodes1() throws AttributeException {
        return values.stream();
    }

    @Override
    public Stream<Node> asStreamOfNodes() {
        try {
            return values.stream()
                    .foldRight(Stream.<Node>builder(), Stream.Builder::add)
                    .build();
        } catch (StreamException e) {
            return Stream.empty();
        }
    }

    public record Builder(JavaList<Node> values) {
        public Builder() {
            this(new JavaList<>());
        }

public        Builder add(Node next) {
            return new Builder(values.add(next));
        }

        public Attribute complete() {
            return new NodesAttribute1(values);
        }
    }
}