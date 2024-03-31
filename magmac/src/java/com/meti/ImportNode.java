package com.meti;

public record ImportNode(String parent, String childString) {

    String render() {
        return "extern " + Lang.IMPORT_KEYWORD + childString() + parent() + ";";
    }
}