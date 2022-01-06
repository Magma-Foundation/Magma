package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Application {
    private final Path source;

    public Application(Path source) {
        this.source = source;
    }

    public Path getSource() {
        return source;
    }

    void run() throws IOException {
        if (Files.exists(getSource())) {
            var fileName = getSource().getFileName().toString();
            var separator = fileName.indexOf('.');
            var name = fileName.substring(0, separator);
            Files.createFile(getSource().resolveSibling(name + ".c"));
        }
    }
}
