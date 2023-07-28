package com.meti.app.compile.clazz;

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

public record Class_(String_ name1, Node body1) implements Node {

    private Option<String_> name() {
        return Some.apply(name1);
    }

    private Option<Node> body() {
        return Some.apply(body1);
    }

    private Option<Node> withBody(Node body) {
        return Some.apply(new Class_(name1, body));
    }

    private Option<List<? extends Node>> lines() {
        return None.apply();
    }

    private Option<Node> type() {
        return None.apply();
    }

    private Option<String_> value() {
        return None.apply();
    }

    private Option<Set<? extends Node>> parameters() {
        return None.apply();
    }

    private Option<Set<String_>> keywords() {
        return None.apply();
    }

    private Option<String_> parent() {
        return None.apply();
    }

    private Option<String_> child() {
        return None.apply();
    }

    private Option<Node> returns() {
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

    private Option<Node> withLines(List<? extends Node> lines) {
        return None.apply();
    }

    private Option<Node> withReturns(Node returns) {
        return None.apply();
    }

    private Option<Node> withParameters(Set<? extends Node> parameters) {
        return None.apply();
    }

    @Override
    public Option<Node> with(String_ key, Attribute attribute) {
        if (key.equalsTo(fromSlice("lines"))) {
            return attribute.asListOfNodes().flatMap(this::withLines);
        } else if (key.equalsTo(fromSlice("body"))) {
            return attribute.asNode().flatMap(this::withBody);
        } else if (key.equalsTo(fromSlice("returns"))) {
            return attribute.asNode().flatMap(this::withReturns);
        } else if (key.equalsTo(fromSlice("parameters"))) {
            return attribute.asSetOfNodes().flatMap(this::withParameters);
        } else {
            return None.apply();
        }
    }

    @Override
    public boolean is(String_ name) {
        throw new UnsupportedOperationException();
    }
}