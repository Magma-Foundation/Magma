package com.meti;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public record SingleSource(Path source) implements Source {
    private Set<Path> listPath1() {
        Set<Path> sources = new HashSet<>();
        if (Files.exists(source())) {
            sources.add(source());
        }
        return sources;
    }

    @Override
    public Set<Location> list() {
        return listPath1().stream().map(Location::new).collect(Collectors.toSet());
    }
}