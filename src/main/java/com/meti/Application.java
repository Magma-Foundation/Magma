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
            var lastName = source.getFileName().toString();
            var separator = lastName.indexOf('.');
            var baseName = lastName.substring(0, separator);
            var target = source.resolveSibling("__%s__.java".formatted(baseName));

            var input = Files.readString(source);
            var output = new MagmaJavaCompiler(input).compile();
            Files.writeString(target, output);
        }
    }

}
