package com.meti;

import java.io.IOException;
import java.util.Set;

public interface SourceSet {
    Set<PathSource> collect() throws IOException;

    String findExtension();
}
