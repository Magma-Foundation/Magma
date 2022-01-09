package com.meti.compile;

import java.util.List;
import java.util.stream.Stream;

public record ListNodeAttribute(List<Node> values) implements Attribute {
    @Override
    public Stream<Node> streamNodes() throws AttributeException {
        return values.stream();
    }
}
