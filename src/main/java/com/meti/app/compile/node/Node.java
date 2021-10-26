package com.meti.app.compile.node;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.app.compile.node.attribute.Attribute;

import java.util.stream.Stream;

public interface Node {
    default Option<Attribute> apply(Attribute.Type type) {
        return new None<>();
    }

    boolean is(Node.Type type);

    default Stream<Attribute.Type> stream(Attribute.Group group) {
        return Stream.empty();
    }

    enum Type {
        Integer, Return, Block, Content, Import
    }
}
