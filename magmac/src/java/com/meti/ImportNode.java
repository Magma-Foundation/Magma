package com.meti;

public record ImportNode(String parent, String childString) {

    String render() {
        return "extern " + Compiler.IMPORT_KEYWORD + childString() + parent() + ";";
    }
}