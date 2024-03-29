package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public record SingleSourceSet(Path source) implements SourceSet {
    private Set<Path> collect1() {
        return Files.exists(source())
                ? Collections.singleton(source())
                : Collections.emptySet();
    }

    @Override
    public Set<PathSource> collect() throws IOException {
        return collect1()
                .stream()
                .map((Path root) -> new PathSource(root.getParent(), root))
                .collect(Collectors.toSet());
    }
}