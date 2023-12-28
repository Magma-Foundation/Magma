package com.meti;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public record SingleSource(Path source) implements Source {

    @Override
    public Set<Path> collectSources() {
        var source = source();
        var sources = new HashSet<Path>();
        if (Files.exists(source)) {
            sources.add(source);
        }
        return sources;
    }
}