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
    public boolean equalsTo(Attribute other) {
        return other.asListOfNodes()
                .map(tuple -> type.equalsTo(tuple.a()) && values.iter()
                        .zip(tuple.b().iter())
                        .allMatch(tuple1 -> tuple1.a().equals(tuple1.b())))
                .unwrapOrElse(false);
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
