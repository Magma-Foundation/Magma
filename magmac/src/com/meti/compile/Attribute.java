package com.meti.compile;

import com.meti.api.collect.JavaString;
import com.meti.api.collect.List;
import com.meti.api.option.None;
import com.meti.api.option.Option;

public interface Attribute {
    default Option<Node> asNode() {
        return None.apply();
    }

    default Option<JavaString> asString() {
        return None.apply();
    }

    default Option<List<Node>> asListOfNodes() {
        return None.apply();
    }
}
