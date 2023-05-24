package com.meti.com.meti;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public record VolatileSingleSource(Path source) {
    Set<Path> collectSources() {
        var sources = new HashSet<Path>();
        if (Files.exists(source())) {
            sources.add(source());
        }
        return sources;
    }
}