package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Collectors;

public class Application {
    static final Path Root = Paths.get(".");
    static final Path SourceDirectory = Root.resolve("source");
    static final Path TestDirectory = SourceDirectory.resolve("test").resolve("magma");
    static final Path MainDirectory = SourceDirectory.resolve("main").resolve("magma");
    static final Path OutDirectory = Root.resolve("out").resolve("c");

    void run() throws ApplicationException {
        createDirectories();
        compileSources();
    }

    private void createDirectories() throws ApplicationException {
        try {
            new com.meti.Path(SourceDirectory).ensureDirectory();
            new com.meti.Path(MainDirectory).ensureDirectory();
            new com.meti.Path(TestDirectory).ensureDirectory();
        } catch (IOException e) {
            throw new ApplicationException(e);
        }
    }

    private void compileSources() throws ApplicationException {
        var classPath = new DirectoryClassPath(SourceDirectory);
        var sourceFiles = collectSourceFiles();

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

                    var input = readInput(relativeToSource);
                    var output = new Compiler(classPath, input).compile();

                    writeOutput(relativeToTarget, output);
                }
            }
        }
    }

    private Set<Path> collectSourceFiles() throws ApplicationException {
        try {
            return Files.walk(MainDirectory).collect(Collectors.toSet());
        } catch (IOException e) {
            throw new ApplicationException(e);
        }
    }

    private String readInput(Path relativeToSource) throws ApplicationException {
        try {
            return Files.readString(relativeToSource);
        } catch (IOException e) {
            throw new ApplicationException(e);
        }
    }

    private void writeOutput(Path relativeToTarget, String output) throws ApplicationException {
        try {
            new com.meti.Path(relativeToTarget.getParent()).ensureDirectory();
            Files.writeString(relativeToTarget, output);
        } catch (IOException e) {
            throw new ApplicationException(e);
        }
    }
}
