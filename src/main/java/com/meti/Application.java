package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Optional;

public final class Application {
    private final Gateway gateway;

    public Application(Gateway gateway) {
        this.gateway = gateway;
    }

    Optional<Path> run() throws IOException {
        var sources = new HashSet<>(gateway.collectSources());
        var targets = new HashSet<Path>();
        for (var source : sources) {
            var thisPackage = source.createPackage();
            var target = gateway.resolveTarget(thisPackage);

            Files.createFile(target);
            targets.add(target);
        }

        return targets.stream().findFirst();
    }

}