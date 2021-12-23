package com.meti;

import com.meti.option.None;
import com.meti.option.Option;

public interface Node {
    default Option<Input> getValueAsInput() {
        return new None<>();
    }

    default Option<Integer> getValueAsInteger() {
        return new None<>();
    }

    default Option<Node> getValueAsNode() {
        return new None<>();
    }

    boolean is(Type type);

    enum Type {
        Integer,
        Content, Return
    }
}
