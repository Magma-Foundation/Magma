package com.meti.source;

import java.util.List;
import java.util.stream.Stream;

public class Package {
    private final List<String> parent;
    private final String name;

    public Package(List<String> parent, String name) {
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
