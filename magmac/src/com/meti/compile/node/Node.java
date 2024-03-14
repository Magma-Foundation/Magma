package com.meti.compile.node;

import com.meti.collect.option.Option;

import java.util.List;

import static com.meti.collect.option.None.None;

public interface Node {
    Option<String> render();

    boolean is(String name);

    default Option<List<? extends Node>> findChildren() {
        return None();
    }

    default Option<Node> withChildren(List<? extends Node> children) {
        return None();
    }

    default Option<Node> findValueAsNode() {
        return None();
    }

    default Option<String> findValueAsString() {
        return None();
    }

    default Option<Integer> findIndent() {
        return None();
    }

    default Option<Node> withValue(Node value) {
        return None();
    }
}
