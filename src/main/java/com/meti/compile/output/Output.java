package com.meti.compile.output;

import com.meti.F1;
import com.meti.compile.Node;
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
