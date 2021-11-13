package com.meti.app;

import java.io.IOException;
import java.nio.file.Files;

public record NIOPath(java.nio.file.Path source) implements Path {
    @Override
    public boolean exists() {
        return Files.exists(source);
    }

    @Override
    public Path extendWith(final String extension) {
        var name = computeFileName();
        return new NIOPath(source.resolveSibling("%s.%s".formatted(name, extension)));
    }

    @Override
    public String computeFileName() {
        var fileName = source.getFileName().toString();
        var separator = fileName.indexOf('.');
        return fileName.substring(0, separator);
    }

    @Override
    public String readAsString() throws IOException {
        return Files.readString(source);
    }

    @Override
    public void writeAsString(String output) throws IOException {
        Files.writeString(source, output);
    }
}
