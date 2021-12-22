package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public record FileWrapper(Path value) {
    static final FileWrapper Root = new FileWrapper(Paths.get("."));

    public String computeRetractedFileName() {
        var name = value.getFileName().toString();
        var separator = name.indexOf('.');
        return name.substring(0, separator);
    }

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

    public FileWrapper resolveSibling(String name) {
        return new FileWrapper(value.resolveSibling(name));
    }
}
