package com.meti.app.compile;

import com.meti.app.Attribute;
import com.meti.app.NodeListAttribute;
import com.meti.core.None;
import com.meti.core.Option;
import com.meti.java.List;
import com.meti.java.Set;
import com.meti.java.String_;

import static com.meti.java.JavaString.fromSlice;

public interface Node {
    default Option<Attribute> apply(String_ key) {
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
        } else {
            return None.apply();
        }
    }

    Option<List<? extends Node>> lines();

    Option<Node> type();

    default Option<Node> withLines(List<? extends Node> lines) {
        return None.apply();
    }

    Option<String_> value();

    default Option<Node> withBody(Node body) {
        return None.apply();
    }

    Option<Node> body();

    Option<String_> name();

    Option<Set<? extends Node>> parameters();

    default Option<Node> withReturns(Node returns) {
        return None.apply();
    }

    default Option<Set<String_>> keywords() {
        return None.apply();
    }

    default Option<String_> parent() {
        return None.apply();
    }

    default Option<String_> child() {
        return None.apply();
    }

    default Option<Node> returns() {
        return None.apply();
    }

    default Option<Node> withParameters(Set<? extends Node> parameters) {
        return None.apply();
    }
}
