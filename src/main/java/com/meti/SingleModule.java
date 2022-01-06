package com.meti;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

public class SingleModule implements Module {
    private final Path source;

    public SingleModule(Path source) {
        this.source = source;
    }

    @Override
    public List<Path> listSources() {
        return Files.exists(getSource()) ? List.of(getSource()) : Collections.emptyList();
    }

    public Path getSource() {
        return source;
    }
}
