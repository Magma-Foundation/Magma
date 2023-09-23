package com.meti;

public record ImportNode(String parent, String child) implements Node {
    @Override
    public String render() {
        return "import { " + child() + " } from " + parent() + ";\n";
    }
}