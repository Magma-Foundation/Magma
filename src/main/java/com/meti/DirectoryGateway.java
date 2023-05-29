package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.stream.Collectors;

public class DirectoryGateway extends PathGateway {
    public DirectoryGateway(Path path) {
        super(path);
    }

    @Override
    public Set<PathSource> collectSources() throws IOException {
        try (var stream = Files.walk(path)) {
            return stream
                    .filter(file -> file.endsWith(".java"))
                    .map(PathSource::new)
                    .collect(Collectors.toSet());
        }
    }
}
