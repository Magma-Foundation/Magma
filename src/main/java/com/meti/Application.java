package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Optional;

public final class Application {
    private final Gateway sourceGateway;
    private final Gateway targetGateway;

    public Application(Gateway sourceGateway, Gateway targetGateway) {
        this.sourceGateway = sourceGateway;
        this.targetGateway = targetGateway;
    }

    Optional<Path> run() throws IOException {
        var sources = new HashSet<>(sourceGateway.collectSources());
        var targets = new HashSet<Path>();
        for (var source : sources) {
            var package_ = source.findPackage();
            var target = targetGateway.resolvePackage(package_);

            Files.createFile(target);
            targets.add(target);
        }

        return targets.stream().findFirst();
    }

}