package com.meti;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Set;

public record SingleGateway(Path path) implements Gateway {
    @Override
    public Set<Path> collectSources() {
        return Files.exists(path()) ? Collections.singleton(path()) : Collections.emptySet();
    }
}