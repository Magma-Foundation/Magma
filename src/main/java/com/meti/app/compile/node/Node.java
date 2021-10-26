package com.meti.app.compile.node;

import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;

import java.util.stream.Stream;

public interface Node {
    Attribute apply(Attribute.Type type) throws AttributeException;

    boolean is(Node.Type type);

    default Stream<Attribute.Type> stream(Attribute.Group group) {
        return Stream.empty();
    }

    enum Type {
        Integer, Return, Block, Content, Import
    }
}
