package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class Application {
    static final Path Root = Paths.get(".");
    static final Path OutDirectory = Root.resolve("out");
    static final Path OutputCDirectory = OutDirectory.resolve("c");
    static final Path SourceDirectory = Root.resolve("source");

    private static void compileDirectory(Path directory) throws IOException {
        var children = Files.list(directory).collect(Collectors.toList());
        for (Path child : children) {
            if (Files.isDirectory(child)) {
                compileDirectory(child);
            } else {
                compileFile(child);
            }
        }
    }

    private static void compileFile(Path child) throws IOException {
        var filePath = child.getFileName().toString();
        var separator = filePath.indexOf('.');
        var name = filePath.substring(0, separator);

        var relativeChild = SourceDirectory.relativize(child);
        var newName = name + ".c";

        var path = OutputCDirectory
                .resolve(relativeChild)
                .resolveSibling(newName);

        Files.createDirectories(path.getParent());
        Files.createFile(path);
    }

    static void run() throws IOException {
        compileDirectory(SourceDirectory);
    }
}
