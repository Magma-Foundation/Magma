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
            Files.createFile(source.resolveSibling("index.h"));
            Files.createFile(source.resolveSibling("index.c"));
            return true;
        } else {
            return false;
        }
    }
}
