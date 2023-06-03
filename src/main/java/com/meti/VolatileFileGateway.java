package com.meti;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public record VolatileFileGateway(Path source) implements PathGateway {
    private Result<Set<Path>> collectSources1() {
        if (Files.exists(source)) {
            return Result.ok(Set.of(source));
        } else {
            return Result.ok(Collections.emptySet());
        }
    }

    @Override
    public Path resolveChild(Source aSource) {
        return this.source.getParent().resolve(aSource.computeName() + ".java");
    }

    @Override
    public Result<Set<Source>> collectSources() {
        return collectSources1().mapValue(sources1 -> sources1.stream()
                .map(PathGateway::createSourceFromPath)
                .collect(Collectors.toSet()));
    }
}