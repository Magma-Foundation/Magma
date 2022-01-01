package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public record Application(Path source) {
    void run() throws IOException {
        if (Files.exists(source)) {
            var input = Files.readString(source);
            var output = new MagmaCCompiler(input).compile();
            var fileName = source.getFileName().toString();
            var separator = fileName.indexOf('.');
            var name = fileName.substring(0, separator);
            Files.writeString(source.resolveSibling(name + ".c"), output);
        }
    }
}
