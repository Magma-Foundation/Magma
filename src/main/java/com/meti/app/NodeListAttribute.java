package com.meti.app;

import com.meti.app.compile.Node;
import com.meti.core.Option;
import com.meti.core.Some;
import com.meti.java.List;

public record NodeListAttribute(List<? extends Node> values) implements Attribute {
    @Override
    public Option<List<? extends Node>> asListOfNodes() {
        return Some.apply(values);
    }

    @Override
    public boolean is(Node.Group group) {
        return group == Node.Group.NodeList;
    }
}
