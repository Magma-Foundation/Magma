package com.meti;

import static com.meti.Options.$Option;

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