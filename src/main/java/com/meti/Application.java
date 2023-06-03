package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Optional;

public final class Application {
    private final PathGateway sourceGateway;
    private final PathGateway targetGateway;

    public Application(PathGateway sourceGateway, PathGateway targetGateway) {
        this.sourceGateway = sourceGateway;
        this.targetGateway = targetGateway;
    }

    Optional<Path> run() throws IOException {
        var sources = sourceGateway.collectSources().unwrapValue();
        var targets = new HashSet<Path>();
        for (var source : sources) {
            targets.add(compile(source));
        }

        return targets.stream().findFirst();
    }

    private Path compile(Source source) throws IOException {
        var target = targetGateway.resolveChild(source);
        Files.createFile(target);
        return target;
    }
}