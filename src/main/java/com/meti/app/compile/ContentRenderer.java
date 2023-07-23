package com.meti.app.compile;

import com.meti.core.Option;
import com.meti.java.String_;

public record ContentRenderer(Node node) implements Renderer {
    @Override
    public Option<String_> render() {
        return node.value();
    }
}
