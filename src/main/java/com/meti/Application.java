package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public record Application(Path source) {
    Path run() throws IOException {
        var fileName = source.getFileName().toString();
        var separator = fileName.indexOf('.');
        var name = fileName.substring(0, separator);
        var target = source.resolveSibling(name + ".c");
        if (Files.exists(source)) {
            Files.createFile(target);
        }
        return target;
    }
}
