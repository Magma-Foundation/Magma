package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Optional;

public final class Application {
    private final Source source;

    public Application(Source source) {
        this.source = source;
    }

    Optional<Path> runSingle() throws IOException {
        return run().stream().findFirst();
    }

    private HashSet<Path> run() throws IOException {
        var sources = source.collectSources();
        var targets = new HashSet<Path>();
        for (var source : sources) {
            var fileNameAsString = source.getFileName().toString();
            var separator = fileNameAsString.indexOf('.');
            var fileName = separator == -1 ? fileNameAsString : fileNameAsString.substring(0, separator);
            var target = source.resolveSibling(fileName + ".mgs");
            Files.createFile(target);
            targets.add(target);
        }
        return targets;
    }
}