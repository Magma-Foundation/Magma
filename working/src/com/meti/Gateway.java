package com.meti;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;

public interface Gateway {
    Set<PathSource> collectSources() throws IOException;

    Path resolvePackage(Package package_);
}
