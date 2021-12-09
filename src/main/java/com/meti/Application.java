package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class Application {
    static final Path Root = Paths.get(".");
    static final Path OutDirectory = Root.resolve("out");
    static final Path OutCDirectory = OutDirectory.resolve("c");
    static final Path SourceDirectory = Root.resolve("source");

    private static String compile(String input) throws ApplicationException {
        String output;
        try {
            output = new MagmaCCompiler(input).compile();
        } catch (CompileException e) {
            throw new ApplicationException(e);
        }
        return output;
    }

    private static void compileDirectory(Path directory) throws ApplicationException {
        var children = listChildren(directory);
        for (Path child : children) {
            if (Files.isDirectory(child)) {
                compileDirectory(child);
            } else {
                compileFile(child);
            }
        }
    }

    private static void compileFile(Path child) throws ApplicationException {
        var input = readInput(child);
        var output = compile(input);

        var filePath = child.getFileName().toString();
        var separator = filePath.indexOf('.');
        var name = filePath.substring(0, separator);

        var relativeChild = SourceDirectory.relativize(child);
        var newName = name + ".c";

        var path = OutCDirectory
                .resolve(relativeChild)
                .resolveSibling(newName);

        writeOutput(output, path);
    }

    private static List<Path> listChildren(Path directory) throws ApplicationException {
        List<Path> children;
        try {
            children = Files.list(directory).collect(Collectors.toList());
        } catch (IOException e) {
            throw new ApplicationException(e);
        }
        return children;
    }

    private static String readInput(Path child) throws ApplicationException {
        String input;
        try {
            input = Files.readString(child);
        } catch (IOException e) {
            throw new ApplicationException(e);
        }
        return input;
    }

    static void run() throws ApplicationException {
        compileDirectory(SourceDirectory);
    }

    private static void writeOutput(String output, Path path) throws ApplicationException {
        try {
            Files.createDirectories(path.getParent());
            Files.writeString(path, output);
        } catch (IOException e) {
            throw new ApplicationException(e);
        }
    }
}
