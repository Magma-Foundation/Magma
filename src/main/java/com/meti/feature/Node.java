package com.meti.feature;

import com.meti.Attribute;
import com.meti.None;
import com.meti.Option;

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
        Integer, Return, Import
    }
}
