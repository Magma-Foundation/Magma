package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class DirectoryModule implements Module {
    private final Path root;

    public DirectoryModule(Path root) {
        this.root = root;
    }

    @Override
    public List<Path> listSources() throws IOException {
        return Files.list(root).collect(Collectors.toList());
    }
}
