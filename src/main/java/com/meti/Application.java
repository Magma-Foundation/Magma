package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public record Application(Path source) {
    Path run() throws IOException {
        if (Files.exists(source)) {
            var fileName = source.getFileName().toString();
            var separator = fileName.indexOf('.');
            var fileNameWithoutSeparator = fileName.substring(0, separator);
            var target = source().resolveSibling(fileNameWithoutSeparator + ".mgs");
            Files.createFile(target);
            return target;
        } else {
            return null;
        }
    }
}