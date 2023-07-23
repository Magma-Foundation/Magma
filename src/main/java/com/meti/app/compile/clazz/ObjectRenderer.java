package com.meti.app.compile.clazz;

import com.meti.app.Attribute;
import com.meti.app.compile.Node;
import com.meti.app.compile.Renderer;
import com.meti.core.Option;
import com.meti.java.String_;

import static com.meti.core.Options.$Option;
import static com.meti.java.JavaString.fromSlice;

public record ObjectRenderer(Node root) implements Renderer {
    @Override
    public Option<String_> render() {
        return $Option(() -> {
            var name = root.apply(fromSlice("name")).flatMap(Attribute::asString).$();
            var body = root
                    .apply(fromSlice("body")).flatMap(Attribute::asNode).$()
                    .apply(fromSlice("value")).flatMap(Attribute::asString).$();
            return fromSlice("object ")
                    .appendOwned(name)
                    .append(" ")
                    .appendOwned(body);
        });
    }
}
