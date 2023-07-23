package com.meti.app.compile.declare;

import com.meti.app.Attribute;
import com.meti.app.NodeListAttribute;
import com.meti.app.compile.*;
import com.meti.core.None;
import com.meti.core.Option;
import com.meti.core.Some;
import com.meti.java.List;
import com.meti.java.Set;
import com.meti.java.String_;

import static com.meti.java.JavaString.fromSlice;

public record Declaration(String_ name1, Node type1) implements Node {
    private Option<String_> name() {
        return Some.apply(name1);
    }

    private Option<Node> type() {
        return Some.apply(type1);
    }

    private Option<List<? extends Node>> lines() {
        return None.apply();
    }

    private Option<String_> value() {
        return None.apply();
    }

    private Option<Node> body() {
        return None.apply();
    }

    @Override
    public Option<Set<? extends Node>> parameters() {
        return None.apply();
    }

    @Override
    public Option<Set<String_>> keywords() {
        return None.apply();
    }

    @Override
    public Option<String_> parent() {
        return None.apply();
    }

    @Override
    public Option<String_> child() {
        return None.apply();
    }

    @Override
    public Option<Node> returns() {
        return None.apply();
    }

    @Override
    public Option<Attribute> apply(String_ key) {
        if (key.equalsTo(fromSlice("lines"))) {
            return lines().map(NodeListAttribute::new);
        } else if (key.equalsTo(fromSlice("type"))) {
            return type().map(NodeAttribute::new);
        } else if (key.equalsTo(fromSlice("value"))) {
            return value().map(StringAttribute::new);
        } else if (key.equalsTo(fromSlice("body"))) {
            return body().map(NodeAttribute::new);
        } else if (key.equalsTo(fromSlice("name"))) {
            return name().map(StringAttribute::new);
        } else if (key.equalsTo(fromSlice("parameters"))) {
            return parameters().map(NodeSetAttribute::new);
        } else if (key.equalsTo(fromSlice("keywords"))) {
            return keywords().map(StringSetAttribute::new);
        } else if (key.equalsTo(fromSlice("parent"))) {
            return parent().map(StringAttribute::new);
        } else if (key.equalsTo(fromSlice("child"))) {
            return child().map(StringAttribute::new);
        } else if (key.equalsTo(fromSlice("returns"))) {
            return returns().map(NodeAttribute::new);
        } else {
            return None.apply();
        }
    }
}
