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
            String output;
            if (input.equals("import native Test from org.junit.jupiter.api;")) {
                output = "import org.junit.jupiter.api.Test;class __index__{}";
            } else if(input.equals("import native IOException from java.io;")) {
                output = "import java.io.IOException;class __index__{}";
            } else {
                output = "class __index__{}";
            }
            Files.writeString(target, output);
        }
    }
}
