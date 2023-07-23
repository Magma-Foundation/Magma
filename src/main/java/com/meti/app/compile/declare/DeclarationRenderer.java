package com.meti.app.compile.declare;

import com.meti.app.Attribute;
import com.meti.app.compile.Node;
import com.meti.app.compile.Renderer;
import com.meti.core.Option;
import com.meti.java.String_;

import static com.meti.core.Options.$Option;
import static com.meti.java.JavaString.fromSlice;

public record DeclarationRenderer(Node node) implements Renderer {
    @Override
    public Option<String_> render() {
        return $Option(() -> {
            var name = node.apply(fromSlice("name")).flatMap(Attribute::asString).$();
            Node node1 = node.apply(fromSlice("type")).flatMap(Attribute::asNode).$();
            var type = node1.apply(fromSlice("value")).flatMap(Attribute::asString).$();

            return name.append(" : ").appendOwned(type);
        });
    }
}
