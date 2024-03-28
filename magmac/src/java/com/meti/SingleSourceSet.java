package com.meti;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public record SingleSourceSet(Path source) implements SourceSet {
    @Override
    public Set<Path> collect() {
        var set = new HashSet<Path>();
        if (Files.exists(source())) {
            set.add(source());
        }
        return set;
    }
}