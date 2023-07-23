package com.meti.app;

import com.meti.app.compile.Node;
import com.meti.core.None;
import com.meti.core.Option;
import com.meti.java.List;

public interface Attribute {
    default Option<List<? extends Node>> asListOfNodes() {
        return None.apply();
    }

    default Option<Node> asNode() {
        return None.apply();
    }
}
