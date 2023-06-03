package com.meti;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public record VolatileFileGateway(Path source) implements PathGateway {
    @Override
    public Result collectSources() {
        if (Files.exists(source)) {
            return Result.ok(Set.of(source));
        } else {
            return Result.ok(Collections.emptySet());
        }
    }
}