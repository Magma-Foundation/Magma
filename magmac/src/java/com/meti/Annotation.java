package com.meti;

public record Annotation(String name, String valueString) {
    String render() {
        return "@" + name() + valueString();
    }
}