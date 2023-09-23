package com.meti;

import static com.meti.Options.$Option;

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