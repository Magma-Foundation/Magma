package com.meti;

import java.nio.file.Path;

public class DirectorySource implements Source {
    private final Path root;

    public DirectorySource(Path root) {
        this.root = root;
    }

    @Override
    public Stream stream() {

        return null;
    }
}
