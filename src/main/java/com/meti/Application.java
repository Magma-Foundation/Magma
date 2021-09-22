package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Application {
    private final Path source;

    public Application(Path source) {
        this.source = source;
    }

    Option<TargetSet> run() throws IOException {
        if (Files.exists(source)) {
            var header = create("index.h");
            var target = create("index.c");
            return new Some<>(new TargetSet(header, target));
        } else {
            return new None<>();
        }
    }

    private Path create(String name) throws IOException {
        return Files.createFile(source.resolveSibling(name));
    }
}
