package com.meti;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Set;

public record SingleGateway(Path path) implements Gateway {
    @Override
    public Path resolveTarget(String package_) {
        return path.resolveSibling(package_ + ".mgs");
    }

    @Override
    public Set<Source> collectSources() {
        return Files.exists(path)
                ? Collections.singleton(new Source(path))
                : Collections.emptySet();
    }
}