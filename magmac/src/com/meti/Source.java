package com.meti;

import java.nio.file.Path;
import java.util.Set;

public interface Source {
    Set<Path> collectSources();
}
