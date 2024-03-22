package com.meti.compile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;

public interface SourceSet {
    Set<Source> collectSources() throws IOException;
}
