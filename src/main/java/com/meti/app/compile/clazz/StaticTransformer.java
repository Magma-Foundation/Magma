package com.meti.app.compile.clazz;

import com.meti.app.compile.Node;
import com.meti.core.Option;

public record StaticTransformer(Node root) implements Transformer {
    @Override
    public Option<Node> transform() {
        return null;
    }
}
