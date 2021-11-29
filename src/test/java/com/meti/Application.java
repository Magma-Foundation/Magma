package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class Application {
    static final Path Root = Paths.get(".");
    static final Path SourceDirectory = Root.resolve("source");
    static final Path TestDirectory = SourceDirectory.resolve("test").resolve("magma");
    static final Path MainDirectory = SourceDirectory.resolve("main").resolve("magma");
    static final Path OutDirectory = Paths.get(".").resolve("out").resolve("c");

    void run() throws IOException {
        ensureDirectory(SourceDirectory);
        ensureDirectory(MainDirectory);
        ensureDirectory(TestDirectory);

        var sourceFiles = Files.walk(MainDirectory).collect(Collectors.toSet());
        for (var relativeToSource : sourceFiles) {
            var relativeSource = MainDirectory.relativize(relativeToSource);

            var fileName = relativeToSource.getFileName().toString();
            var separator = fileName.indexOf('.');
            if (separator != -1) {
                var name = fileName.substring(0, separator);
                var extension = fileName.substring(separator + 1);
                if (extension.equals("mgf")) {
                    var relativeTarget = relativeSource.resolveSibling(name + ".c");
                    var relativeToTarget = OutDirectory.resolve(relativeTarget);

                    ensureDirectory(relativeToTarget.getParent());

                    var input = Files.readString(relativeToSource);
                    if (input.isBlank()) {
                        Files.createFile(relativeToTarget);
                    } else {
                        var start = input.indexOf('{');
                        var structureName = input.substring("struct ".length(), start).trim();
                        Files.writeString(relativeToTarget, "struct " + structureName + " {};");
                    }
                }
            }
        }
    }

    static void ensureDirectory(Path path) throws IOException {
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
    }
}
