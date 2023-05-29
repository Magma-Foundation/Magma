package com.meti;

public record MagmaImport(Node segment) implements Node {
    @Override
    public String render() {
        var s = segment().render();
        return "import " + s + ";";
    }
}