package com.meti.compile.function;

import com.meti.api.option.Option;
import com.meti.compile.Node;
import com.meti.compile.Renderer;

import static com.meti.api.option.Options.$Option;

public record FunctionRenderer(Node node) implements Renderer {

    @Override
    public Option<String> render() {
        return $Option(() -> {
            var name = this.node().getName().$();
            var parameters = this.node().getParameters().$();
            var body = this.node().getBody().$();
            return "export class def " + name + parameters + " => " + body;
        });
    }
}