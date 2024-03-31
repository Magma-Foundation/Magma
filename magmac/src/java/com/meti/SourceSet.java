package com.meti;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;

public interface SourceSet {
    Set<PathSource> collect() throws IOException;
}
