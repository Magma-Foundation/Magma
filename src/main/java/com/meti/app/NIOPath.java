package com.meti.app;

import java.io.IOException;
import java.nio.file.Files;

public record NIOPath(java.nio.file.Path source) implements Path {
    @Override
    public boolean exists() {
        return Files.exists(getSource());
    }

    @Override
    public java.nio.file.Path getSource() {
        return source;
    }

    @Override
    public java.nio.file.Path extendWith(final String extension) {
        var name = computeFileName();
        return getSource().resolveSibling("%s.%s".formatted(name, extension));
    }

    @Override
    public String computeFileName() {
        var fileName = getSource().getFileName().toString();
        var separator = fileName.indexOf('.');
        return fileName.substring(0, separator);
    }

    @Override
    public String readAsString() throws IOException {
        return Files.readString(getSource());
    }

    @Override
    public void writeAsString(String output) throws IOException {
        Files.writeString(getSource(), output);
    }
}
