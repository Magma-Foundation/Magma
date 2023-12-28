package com.meti;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public record SingleSource(Path source) implements Source {

    @Override
    public Set<Path> collectSources() {
        var source = source();
        var sources = new HashSet<Path>();
        if (Files.exists(source)) {
            sources.add(source);
        }
        return sources;
    }

    @Override
    public Stream<Unit> streamUnits() {
        /*
        This could become a bug at some point if source1.getParent() is null,
        and thus a new implementation of Unit should be made.
        But this class is only used in testing...

        - Gavin, 12/28/2023
         */
        return collectSources()
                .stream()
                .map((Path source1) -> new Unit(source1, source1.getParent()));
    }
}