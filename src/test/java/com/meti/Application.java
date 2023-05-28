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
        var sources = gateway.collectSources();
        var targets = new HashSet<Path>();
        for (var source : sources) {
            var fileName = source.getFileName().toString();
            var separator = fileName.indexOf('.');
            var fileNameWithoutExtension = fileName.substring(0, separator);
            var target = source.resolveSibling(fileNameWithoutExtension + ".mgs");
            Files.createFile(target);
            targets.add(target);
        }

        return targets.stream().findFirst();
    }
}