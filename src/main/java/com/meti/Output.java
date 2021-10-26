package com.meti;

import com.meti.feature.Node;

public interface Output {
    default Option<Node> asNode() {
        return new None<>();
    }

    default Option<String> asString() {
        return new None<>();
    }

    default <E extends Exception> Output mapNodes(F1<Node, Output, E> mapper) throws E {
        return this;
    }
}
