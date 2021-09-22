package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Application {
    private final Path source;

    public Application(Path source) {
        this.source = source;
    }

    void run() throws IOException {
        if (Files.exists(source)) {
            var fileName = source.getFileName();
            var fileNameString = fileName.toString();
            var separator = fileNameString.indexOf('.');
            var fileNameWithoutSeparator = fileNameString.substring(0, separator);
            Files.createFile(source.resolveSibling(fileNameWithoutSeparator + ".c"));
        }
    }
}
