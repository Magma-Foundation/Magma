package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class NIOPath {
    static final NIOPath Root = new NIOPath(Paths.get("."));
    private final Path target;

    public NIOPath(Path target) {
        this.target = target;
    }

    NIOPath resolve(String child) {
        return new NIOPath(target.resolve(child));
    }

    void create() throws IOException {
        Files.createFile(target);
    }

    void deleteIfExists() throws IOException {
        Files.deleteIfExists(target);
    }

    boolean exists() {
        return Files.exists(target);
    }
}
