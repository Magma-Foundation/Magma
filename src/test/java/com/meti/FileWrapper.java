package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public record FileWrapper(Path value) {
    static final FileWrapper Root = new FileWrapper(Paths.get("."));

    public FileWrapper create() throws IOException {
        Files.createFile(value);
        return this;
    }

    public void deleteIfExists() throws IOException {
        Files.deleteIfExists(value);
    }

    public boolean exists() {
        return Files.exists(value);
    }

    FileWrapper resolve(String name) {
        return new FileWrapper(value.resolve(name));
    }
}
