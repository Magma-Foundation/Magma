package com.meti;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;
import java.util.stream.Stream;

public interface Source {
    Set<Path> collectSources() throws IOException;

    Stream<Unit> streamUnits() throws IOException;
}
