package com.meti;

public record Annotation(String name, String valueString) {
    String renderAnnotation() {
        return "\n\t@" + name() + valueString();
    }
}