package com.meti.compile.attribute;

import com.meti.api.collect.JavaString;
import com.meti.api.collect.List;
import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.compile.node.Node;

public interface Attribute {
    default Option<Node> asNode() {
        return None.apply();
    }

    default Option<JavaString> asString() {
        return None.apply();
    }

    default Option<List<? extends Node>> asListOfNodes() {
        return None.apply();
    }

    JavaString toXML();
}
