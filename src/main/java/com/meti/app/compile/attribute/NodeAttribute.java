package com.meti.app.compile.attribute;

import com.meti.app.compile.Node;
import com.meti.core.Option;
import com.meti.core.Some;
import com.meti.core.Tuple;
import com.meti.java.String_;

public record NodeAttribute(String_ type, Node value) implements Attribute {

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean is(Node.Group group) {
        return group == Node.Group.Node;
    }

    @Override
    public Option<Tuple<String_, Node>> asNode() {
        return Some.apply(new Tuple<>(type, value));
    }

    @Override
    public boolean equalsTo(Attribute other) {
        return other.asNode()
                .map(tuple -> type.equalsTo(tuple.a()) && value.equalsTo(tuple.b()))
                .unwrapOrElse(false);
    }
}
