package com.meti.app.compile.attribute;

import com.meti.app.compile.Node;
import com.meti.core.Option;
import com.meti.core.Some;
import com.meti.core.Tuple;
import com.meti.java.List;
import com.meti.java.String_;

public record NodeListAttribute(String_ type, List<? extends Node> values) implements Attribute {
    @Override
    public String toString() {
        return values.toString();
    }

    @Override
    public boolean is(Node.Group group) {
        return group == Node.Group.NodeList;
    }

    @Override
    public Option<Tuple<String_, List<? extends Node>>> asListOfNodes() {
        return Some.apply(new Tuple<>(type, values));
    }
}
