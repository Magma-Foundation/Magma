package com.meti.compile.node.output;

import com.meti.F1;
import com.meti.compile.node.Node;
import com.meti.option.None;
import com.meti.option.Option;

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
