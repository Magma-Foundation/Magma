package com.meti;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public record SingleSource(Path source) implements Source {
    @Override
    public Set<Path> listPath() {
        Set<Path> sources = new HashSet<>();
        if (Files.exists(source())) {
            sources.add(source());
        }
        return sources;
    }
}