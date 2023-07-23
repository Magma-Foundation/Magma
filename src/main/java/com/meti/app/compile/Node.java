package com.meti.app.compile;

import com.meti.core.None;
import com.meti.core.Option;
import com.meti.java.List;
import com.meti.java.String_;

public interface Node {
    default Option<List<? extends Node>> lines() {
        return None.apply();
    }

    default Option<Node> withLines(List<? extends Node> lines) {
        return None.apply();
    }

    default Option<String_> value() {
        return None.apply();
    }

    default Option<Node> withBody(Node body) {
        return None.apply();
    }

    default Option<Node> body() {
        return None.apply();
    }

    default Option<String_> name() {
        return None.apply();
    }
}
