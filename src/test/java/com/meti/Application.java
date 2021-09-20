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
            var nativeName = source.getName(source.getNameCount() - 1)
                    .toString();
            var separator = nativeName.indexOf('.');
            String name;
            if (separator == -1) {
                name = nativeName;
            } else {
                name = nativeName.substring(0, separator);
            }

            var targetHeader = source.resolveSibling(name + ".h");
            var targetSource = source.resolveSibling(name + ".c");

            Files.createFile(targetHeader);
            Files.createFile(targetSource);
        }
    }
}
