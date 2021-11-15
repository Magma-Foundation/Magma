package com.meti.app.node;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.attribute.Attribute;
import com.meti.app.attribute.AttributeException;

import java.util.stream.Stream;

public interface Node {
    default Attribute apply(Attribute.Type type) throws AttributeException {
        throw new AttributeException("No attribute exists for type: " + type);
    }

    default Option<Attribute> applyOptionally(Attribute.Type type) {
        try {
            return new Some<>(apply(type));
        } catch (AttributeException e) {
            return new None<>();
        }
    }

    boolean is(Type type);

    default Stream<Attribute.Type> stream(Attribute.Group group) {
        return Stream.empty();
    }

    default Node with(Attribute.Type type, Attribute value) throws AttributeException {
        return this;
    }

    enum Type {
        Block,
        Content,
        Function,
        Int,
        Primitive,
        Return,
        Sequence, Import, Declaration,
    }
}
