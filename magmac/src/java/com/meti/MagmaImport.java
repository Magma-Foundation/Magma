package com.meti;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public record MagmaImport(String parent, Optional<String> defaultValue, List<String> children) implements Node {
    public MagmaImport(String parent, String child) {
        this(parent, Optional.empty(), Collections.singletonList(child));
    }

    @Override
    public String render() {
        var defaultString = defaultValue.map(value -> children.isEmpty() ? value : value + ", ").orElse("");
        var childrenString = children.isEmpty() ? "" : ("{" + String.join(", ", children) + "}");

        return Compiler.renderImport("%s%s from %s".formatted(defaultString, childrenString, parent()));
    }
}