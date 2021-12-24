package com.meti.io;

import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public record PathWrapper(Path value) {
    public static final PathWrapper Root = new PathWrapper(Paths.get("."));

    public String computeRetractedFileName() {
        var name = value.getFileName().toString();
        var separator = name.indexOf('.');
        return name.substring(0, separator);
    }

    public File createAsFile() throws IOException {
        Files.createFile(value);
        return new FileImpl();
    }

    public void deleteIfExists() throws IOException {
        Files.deleteIfExists(value);
    }

    public boolean exists() {
        return Files.exists(value);
    }

    public Option<File> existsAsFile() {
        return Files.exists(value) && Files.isRegularFile(value)
                ? new Some<>(new FileImpl())
                : new None<>();
    }

    public PathWrapper resolve(String name) {
        return new PathWrapper(value.resolve(name));
    }

    public PathWrapper resolveSibling(String name) {
        return new PathWrapper(value.resolveSibling(name));
    }

    private class FileImpl implements File {
        @Override
        public PathWrapper asPath() {
            return PathWrapper.this;
        }

        @Override
        public String readString() throws IOException {
            return Files.readString(value);
        }

        @Override
        public File writeString(String value) throws IOException {
            Files.writeString(PathWrapper.this.value, value);
            return this;
        }
    }
}
