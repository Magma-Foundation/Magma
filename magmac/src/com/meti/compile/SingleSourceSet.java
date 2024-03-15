package com.meti.compile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public record SingleSourceSet(Path source) implements SourceSet {
    @Override
    public Set<Source> collectSources() {
        var sources = new HashSet<Source>();
        if (Files.exists(source())) {
            sources.add(new PathSource(source.getParent(), source));
        }
        return sources;
    }
}