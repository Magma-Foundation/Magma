package com.meti.app.compile;

import com.meti.app.Attribute;
import com.meti.core.Option;
import com.meti.core.Some;

public record NodeAttribute(Node node) implements Attribute {
    @Override
    public Option<Node> asNode() {
        return Some.apply(node);
    }
}
