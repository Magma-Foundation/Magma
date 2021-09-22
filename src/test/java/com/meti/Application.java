package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Application {
    private final Path source;

    public Application(Path source) {
        this.source = source;
    }

    boolean run() throws IOException {
        if (Files.exists(source)) {
            create("index.h");
            create("index.c");
            return true;
        } else {
            return false;
        }
    }

    private void create(String name) throws IOException {
        Files.createFile(source.resolveSibling(name));
    }
}
