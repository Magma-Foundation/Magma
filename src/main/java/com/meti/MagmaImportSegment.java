package com.meti;

import java.util.List;

public record MagmaImportSegment(List<String> namespace, String name) implements Node {
    @Override
    public String render() {
        var joinedNamespace = String.join(".", namespace());
        return "{ " + name() + " } from " + joinedNamespace;
    }
}