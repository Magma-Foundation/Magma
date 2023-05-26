package com.meti.node;

public record Statement(Node value) {
    String render() {
        return value().render() + ";";
    }
}