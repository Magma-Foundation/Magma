package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class Application {
    private final Path source;

    public Application(Path source) {
        this.source = source;
    }

    Path run() throws IOException {
        if (Files.exists(source)) {
            var fileName = source.getFileName().toString();
            var separator = fileName.indexOf('.');
            var fileNameWithoutExtension = fileName.substring(0, separator);
            var target = source.resolveSibling(fileNameWithoutExtension + ".java");
            Files.createFile(target);
            return target;
        } else {
            return null;
        }
    }
}