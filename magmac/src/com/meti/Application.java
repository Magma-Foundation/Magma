package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public record Application(Path source) {
    Optional<Path> run() throws IOException {
        if (Files.exists(source())) {
            return compileSource();
        }
        return Optional.empty();
    }

    private Optional<Path> compileSource() throws IOException {
        var fileName = source().getFileName().toString();
        var separator = fileName.indexOf('.');
        var fileNameWithoutExtension = separator == -1 ? fileName : fileName.substring(0, separator);
        var target = source().resolveSibling(fileNameWithoutExtension + ".mgs");
        Files.createFile(target);
        return Optional.of(target);
    }
}