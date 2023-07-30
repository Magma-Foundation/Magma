package com.meti.app.compile;

import com.meti.app.Attribute;
import com.meti.core.Option;
import com.meti.core.Some;
import com.meti.java.Set;

public record NodeSetAttribute(Set<? extends Node> values) implements Attribute {
    @Override
    public Option<Set<? extends Node>> asSetOfNodes() {
        return Some.apply(values);
    }

    @Override
    public boolean is(Node.Group group) {
        throw new UnsupportedOperationException();
    }
}
