package com.meti.app.compile;

import com.meti.app.compile.attribute.Attribute;
import com.meti.core.Option;
import com.meti.java.String_;

import static com.meti.java.JavaString.fromSlice;

public record ContentRenderer(Node node) implements Renderer {
    @Override
    public Option<String_> render() {
        return node.applyOptionally(fromSlice("value")).flatMap(Attribute::asString);
    }
}
