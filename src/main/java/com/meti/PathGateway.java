package com.meti;

import java.nio.file.Path;
import java.util.Set;

public interface PathGateway {
    static Source createSourceFromPath(Path source) {
        var fileName = source.getFileName().toString();
        var separator = fileName.indexOf('.');
        var fileNameWithoutExtension = fileName.substring(0, separator);
        return new Source(fileNameWithoutExtension);
    }

    Path resolveChild(Source aSource);

    Result<Set<Source>> collectSources();
}
