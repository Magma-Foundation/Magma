package com.meti.io;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public record SingleSourceSet(Path source) implements SourceSet {
    @Override
    public Set<PathSource> collect() {
        var source = source();
        var set = new HashSet<PathSource>();
        if (Files.exists(source)) set.add(new PathSource(source.getParent(), source));
        return set;
    }
}