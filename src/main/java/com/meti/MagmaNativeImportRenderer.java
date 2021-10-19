package com.meti;

public record MagmaNativeImportRenderer(String name) {
    String render() {
        return "import native " + name();
    }
}