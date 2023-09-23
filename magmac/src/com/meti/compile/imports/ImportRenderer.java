package com.meti.compile.imports;

import com.meti.api.option.Option;
import com.meti.compile.Node;
import com.meti.compile.Renderer;

import static com.meti.api.option.Options.$Option;

public record ImportRenderer(Node importNode) implements Renderer {

    @Override
    public Option<String> render() {
        return $Option(() -> {
            var child = this.importNode().getChild().$();
            var parent = this.importNode().getParent().$();
            return "import { " + child + " } from " + parent + ";\n";
        });
    }
}