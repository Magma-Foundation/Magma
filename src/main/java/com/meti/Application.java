package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Optional;

public final class Application {
    private final PathGateway sourceGateway;

    public Application(PathGateway sourceGateway) {
        this.sourceGateway = sourceGateway;
    }

    Optional<Path> run() throws IOException {
        var sources = sourceGateway.collectSources().unwrapValue();
        var targets = new HashSet<Path>();
        for (var source : sources) {
            targets.add(compile(source));
        }

        return targets.stream().findFirst();
    }

    private Path compile(Path source) throws IOException {
        var fileName = source.getFileName().toString();
        var separator = fileName.indexOf('.');
        var fileNameWithoutExtension = fileName.substring(0, separator);
        var target = source.resolveSibling(fileNameWithoutExtension + ".java");
        Files.createFile(target);
        return target;
    }
}