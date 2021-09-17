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
            var name = source.getFileName();
            var fileNameWithExtension = name.getName(name.getNameCount() - 1)
                    .toString();
            var separator = fileNameWithExtension.indexOf('.');
            var packageName = separator == -1 ?
                    fileNameWithExtension :
                    fileNameWithExtension.substring(0, separator);
            var target = source.resolveSibling(packageName + ".c");
            var input = Files.readString(source);
            var output = new Compiler(packageName, input).compile();
            Files.writeString(target, output);
        }
    }
}
