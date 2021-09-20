package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

public class DirectorySource implements Source {
    private final Path root;

    public DirectorySource(Path root) {
        this.root = root;
    }

    @Override
    public Stream<Script> stream() throws IOException {
        var list = Files.walk(root)
                .collect(Collectors.toList());
        return new JavaListStream<>(list).map(Script::new);
    }
}
