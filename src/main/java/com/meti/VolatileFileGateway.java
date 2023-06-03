package com.meti;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Set;

public record VolatileFileGateway(Path source) implements PathGateway {

    @Override
    public Path resolveChild(Source aSource) {
        return this.source.getParent().resolve(aSource.computeName() + ".java");
    }

    @Override
    public Result<Set<Source>> collectSources() {
        if (Files.exists(source)) {
            return Result.ok(Collections.singleton(new PathSource(source.getParent(), source)));
        } else {
            return Result.ok(Collections.emptySet());
        }
    }
}