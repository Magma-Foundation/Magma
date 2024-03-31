package com.meti.java;

import com.meti.Lang;

public record ImportNode(String parent, String childString) {

    public String render() {
        return "extern " + Lang.IMPORT_KEYWORD + childString() + parent() + ";";
    }
}