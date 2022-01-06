package com.meti;

import java.nio.file.Path;
import java.nio.file.Paths;

public class NIOPath {
    static final NIOPath Root = new NIOPath(Paths.get("."));
    private final Path value;

    public NIOPath(Path value) {
        this.value = value;
    }

    Path resolveChild(String name) {
        return getValue().resolve(name);
    }

    public Path getValue() {
        return value;
    }
}
