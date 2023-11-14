package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Optional;

public final class Application {
    private final Source source;
    private final Target target;

    public Application(Source source, Target target) {
        this.source = source;
        this.target = target;
    }

    Optional<Path> run() throws IOException {
        var sources = new HashSet<>(source.list());

        var targets = new HashSet<Path>();
        for (var location : sources) {
            var input = location.read();
            var output = new Compiler(input).compile();
            var target = this.target.resolveTarget(location, ".mgs");

            var parent = target.getParent();
            if (!Files.exists(parent)) {
                Files.createDirectories(parent);
            }

            Files.writeString(target, output);
            targets.add(target);
        }

        return targets.stream().findFirst();
    }
}