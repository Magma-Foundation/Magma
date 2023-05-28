package com.meti;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;

public interface Gateway {
    Path resolveTarget(String package_);

    Set<Source> collectSources() throws IOException;
}
