package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public record NIOPath(Path value) implements com.meti.Path {
    public static final com.meti.Path Root = new NIOPath(Paths.get("."));

    @Override
    public String computeFileNameWithoutExtension() {
        var fileName = value.getFileName().toString();
        var separator = fileName.indexOf('.');
        return fileName.substring(0, separator);
    }

    @Override
    public File create() throws com.meti.IOException {
        try {
            Files.createFile(value);
            return new FileImpl();
        } catch (IOException e) {
            throw new com.meti.IOException(e);
        }
    }

    @Override
    public void deleteIfExists() throws com.meti.IOException {
        try {
            Files.deleteIfExists(value);
        } catch (IOException e) {
            throw new com.meti.IOException(e);
        }
    }

    @Override
    public Option<File> existing() {
        return Files.exists(value)
                ? new Some<>(new FileImpl())
                : new None<>();
    }

    @Override
    public boolean exists() {
        return Files.exists(value);
    }

    @Override
    public com.meti.Path resolveChild(String child) {
        return new NIOPath(value.resolve(child));
    }

    @Override
    public com.meti.Path resolveSibling(String sibling) {
        return new NIOPath(value.resolveSibling(sibling));
    }

    private class FileImpl implements File {
        @Override
        public String readAsString() throws com.meti.IOException {
            try {
                return Files.readString(value);
            } catch (IOException e) {
                throw new com.meti.IOException(e);
            }
        }

        @Override
        public void writeAsString(String output) throws com.meti.IOException {
            try {
                Files.writeString(value, output);
            } catch (IOException e) {
                throw new com.meti.IOException(e);
            }
        }
    }
}
