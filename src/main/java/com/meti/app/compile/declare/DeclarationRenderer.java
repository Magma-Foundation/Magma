package com.meti.app.compile.declare;

import com.meti.app.compile.Node;
import com.meti.app.compile.Renderer;
import com.meti.core.Option;
import com.meti.java.String_;

import static com.meti.core.Options.$Option;

public record DeclarationRenderer(Node node) implements Renderer {
    @Override
    public Option<String_> render() {
        return $Option(() -> {
            var name = node.name().$();
            var type = node.type().$()
                    .value().$();

            return name.append(" : ").appendOwned(type);
        });
    }
}
