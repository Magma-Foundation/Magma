package com.meti.app.compile.attribute;

import com.meti.app.compile.Node;
import com.meti.core.None;
import com.meti.core.Option;
import com.meti.core.Some;
import com.meti.core.Tuple;
import com.meti.java.List;
import com.meti.java.String_;

public record StringAttribute(String_ value) implements Attribute {
    @Override
    public Option<String_> asString() {
        return Some.apply(value);
    }

    @Override
    public boolean equalsTo(Attribute other) {
        return other.asString()
                .map(this.value::equalsTo)
                .unwrapOrElse(false);
    }

    @Override
    public String toString() {
        return "'" + value.toString() + "'";
    }

    @Override
    public boolean is(Node.Group group) {
        return group == Node.Group.String;
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
