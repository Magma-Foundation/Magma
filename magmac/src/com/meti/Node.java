package com.meti;

import static com.meti.None.None;

public interface Node {
    Option<String> render();

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
