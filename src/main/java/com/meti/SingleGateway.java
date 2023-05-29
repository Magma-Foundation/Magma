package com.meti;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Set;

public final class SingleGateway extends PathGateway {
    public SingleGateway(Path path) {
        super(path);
    }

    @Override
    public Set<PathSource> collectSources() {
        return Files.exists(path)
                ? Collections.singleton(new PathSource(path.getParent(), path))
                : Collections.emptySet();
    }
}