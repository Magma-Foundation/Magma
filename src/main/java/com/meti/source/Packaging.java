package com.meti.source;

import java.util.List;
import java.util.stream.Stream;

public class Packaging {
    private final List<String> parent;
    private final String name;

    public Packaging(String name, String... parent) {
        this(name, List.of(parent));
    }

    public Packaging(String name, List<String> parent) {
        this.parent = parent;
        this.name = name;
    }

    public String computeName() {
        return name;
    }

    public Stream<String> parent() {
        return parent.stream();
    }
}
