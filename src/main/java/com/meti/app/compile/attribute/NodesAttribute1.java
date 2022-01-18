package com.meti.app.compile.attribute;

import com.meti.api.collect.java.List;
import com.meti.api.collect.stream.StreamException;
import com.meti.app.compile.node.Node;

import java.util.stream.Stream;

public record NodesAttribute1(List<Node> values) implements Attribute {
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

    @Override
    public com.meti.api.collect.stream.Stream<Node> asStreamOfNodes1() throws AttributeException {
        return values.stream();
    }

    public record Builder(List<Node> values) {
        public Builder() {
            this(List.createList());
        }

        public Builder add(Node next) {
            return new Builder(values.add(next));
        }

        public Attribute complete() {
            return new NodesAttribute1(values);
        }
    }
}
