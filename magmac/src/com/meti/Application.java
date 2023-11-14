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

    Optional<Path> run() throws IOException {
        var sources = source.listPath();

        var targets = new HashSet<Path>();
        for (Path path : sources) {
            var fileName = path.getFileName().toString();
            var separator = fileName.indexOf('.');
            var name = fileName.substring(0, separator);
            var target = path.resolveSibling(name + ".mgs");

            var input = Files.readString(path);
            var output = new Compiler(input).compile();
            Files.writeString(target, output);
            targets.add(target);
        }

        return targets.stream().findFirst();
    }
}