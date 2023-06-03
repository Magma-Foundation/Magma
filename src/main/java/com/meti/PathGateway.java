package com.meti;

import java.nio.file.Path;
import java.util.Set;

public interface PathGateway {
    Path resolveChild(Source aSource);

    Result<Set<Source>> collectSources();
}
