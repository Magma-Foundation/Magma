package com.meti;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;

public interface Source {
    Set<Path> collectSources() throws IOException;
}
