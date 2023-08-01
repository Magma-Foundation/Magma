package com.meti.app.compile.attribute;

import com.meti.app.compile.Node;
import com.meti.core.None;
import com.meti.core.Option;
import com.meti.core.Some;
import com.meti.core.Tuple;
import com.meti.java.JavaSet;
import com.meti.java.List;
import com.meti.java.Set;
import com.meti.java.String_;

public record StringSetAttribute(Set<String_> values) implements Attribute {
    public StringSetAttribute() {
        this(JavaSet.empty());
    }

    @Override
    public Option<Set<String_>> asSetOfStrings() {
        return Some.apply(values);
    }

    @Override
    public String toString() {
        return values.toString();
    }

    @Override
    public boolean is(Node.Group group) {
        return group == Node.Group.StringSet;
    }

    @Override
    public Option<Tuple<String_, Node>> asNode() {
        return asNode1().map(value -> {
            throw new UnsupportedOperationException();
        });
    }

    private Option<Node> asNode1() {
        return None.apply();
    }

    @Override
    public Option<Tuple<String_, List<? extends Node>>> asListOfNodes() {
        return asListOfNodes1().map(value -> {
            throw new UnsupportedOperationException();
        });
    }

    private Option<List<? extends Node>> asListOfNodes1() {
        return None.apply();
    }
}
