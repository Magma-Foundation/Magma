package com.meti.node;

public record Statement(Node value) {
    String render() {
        return new MagmaRenderer(this.value()).render().orElseThrow() + ";";
    }
}