package com.meti.feature;

import com.meti.attribute.Attribute;
import com.meti.option.None;
import com.meti.option.Option;

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
