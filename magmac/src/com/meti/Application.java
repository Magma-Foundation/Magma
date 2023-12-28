package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public record Application(Path source) {
    Optional<Path> run() throws IOException {
        if (Files.exists(source())) {
            var fileNameAsString = source().getFileName().toString();
            var separator = fileNameAsString.indexOf('.');
            var fileName = separator == -1 ? fileNameAsString : fileNameAsString.substring(0, separator);
            var target = source().resolveSibling(fileName + ".mgs");
            Files.createFile(target);
            return Optional.of(target);
        } else {
            return Optional.empty();
        }
    }
}