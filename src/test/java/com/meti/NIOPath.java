package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class NIOPath {
    private final Path path;

    public NIOPath(Path path) {
        this.path = path;
    }

    void ensureAsFile() throws IOException {
        if (!Files.exists(path)) {
            Files.createFile(path);
        }
    }
}
