package com.meti;

public record Statement(Node value) {
    String render() {
        return value().render() + ";";
    }
}