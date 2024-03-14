package com.meti;

import java.util.List;

import static com.meti.None.None;

public interface Node {
    Option<String> render();

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
