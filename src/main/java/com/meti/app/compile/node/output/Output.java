package com.meti.app.compile.node.output;

import com.meti.api.F1;
import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.app.compile.node.Node;

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
