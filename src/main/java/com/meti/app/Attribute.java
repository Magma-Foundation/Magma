package com.meti.app;

import com.meti.app.compile.Node;
import com.meti.core.None;
import com.meti.core.Option;
import com.meti.java.List;
import com.meti.java.String_;

public interface Attribute {
    default Option<List<? extends Node>> asListOfNodes() {
        return None.apply();
    }

    default Option<Node> asNode() {
        return None.apply();
    }

    default Option<String_> asString() {
        return None.apply();
    }
}
