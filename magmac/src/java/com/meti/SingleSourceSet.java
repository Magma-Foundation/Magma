package com.meti;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Set;

public record SingleSourceSet(Path source) implements SourceSet {
    @Override
    public Set<Path> collect() {
        return Files.exists(source())
                ? Collections.singleton(source())
                : Collections.emptySet();
    }
}