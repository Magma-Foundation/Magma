package com.meti;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public record MagmaImport(String parent, List<String> child) implements Node {
    public MagmaImport(String parent, String child) {
        this(parent, Collections.singletonList(child));
    }

    public MagmaImport(String parent, List<String> child) {
        this.parent = parent;
        this.child = child;
    }

    @Override
    public String render() {
        return Compiler.renderImport("{" + String.join(", ", child) + "} from " + parent());
    }
}