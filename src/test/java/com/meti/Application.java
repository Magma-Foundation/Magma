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
    static final Path OutDirectory = Root.resolve("out").resolve("c");

    void run() throws IOException {
        new com.meti.Path(SourceDirectory).ensureDirectory();
        new com.meti.Path(MainDirectory).ensureDirectory();
        new com.meti.Path(TestDirectory).ensureDirectory();

        var sourceFiles = Files.walk(MainDirectory).collect(Collectors.toSet());
        for (var relativeToSource : sourceFiles) {
            var relativeSource = MainDirectory.relativize(relativeToSource);

            var fileName = relativeToSource.getFileName().toString();
            var separator = fileName.indexOf('.');
            if (separator != -1) {
                var name = fileName.substring(0, separator);
                var extension = fileName.substring(separator + 1);
                if (extension.equals("mgf")) {
                    var relativeTarget = relativeSource.resolveSibling(name + ".h");
                    var relativeToTarget = OutDirectory.resolve(relativeTarget);

                    new com.meti.Path(relativeToTarget.getParent()).ensureDirectory();

                    var input = Files.readString(relativeToSource);
                    var output = new Compiler(input).compile();
                    Files.writeString(relativeToTarget, output);
                }
            }
        }
    }
}
