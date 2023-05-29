package com.meti;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Set;

public final class SingleGateway extends PathGateway {
    private final Path child;

    public SingleGateway(Path parent, Path child) {
        super(parent);
        this.child = child;
    }

    @Override
    public Set<PathSource> collectSources() {
        return Files.exists(child)
                ? Collections.singleton(new PathSource(directory, child))
                : Collections.emptySet();
    }
}