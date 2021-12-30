package com.meti.api.io;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public record NIOFile(Path value) implements File {
    public static final com.meti.api.io.Path Root = new NIOFile(Paths.get("."));

    @Override
    public String computeFileNameWithoutExtension() {
        var fileName = value.getFileName().toString();
        var separator = fileName.indexOf('.');
        return fileName.substring(0, separator);
    }

    @Override
    public File create() throws com.meti.api.io.IOException {
        try {
            Files.createFile(value);
            return this;
        } catch (IOException e) {
            throw new com.meti.api.io.IOException(e);
        }
    }

    @Override
    public void deleteIfExists() throws com.meti.api.io.IOException {
        try {
            Files.deleteIfExists(value);
        } catch (IOException e) {
            throw new com.meti.api.io.IOException(e);
        }
    }

    @Override
    public Option<File> existing() {
        return Files.exists(value)
                ? new Some<>(this)
                : new None<>();
    }

    @Override
    public boolean exists() {
        return Files.exists(value);
    }

    @Override
    public com.meti.api.io.Path resolveChild(String child) {
        return new NIOFile(value.resolve(child));
    }

    @Override
    public com.meti.api.io.Path resolveSibling(String sibling) {
        return new NIOFile(value.resolveSibling(sibling));
    }

    @Override
    public String readAsString() throws com.meti.api.io.IOException {
        try {
            return Files.readString(value);
        } catch (IOException e) {
            throw new com.meti.api.io.IOException(e);
        }
    }

    @Override
    public File writeAsString(String output) throws com.meti.api.io.IOException {
        try {
            Files.writeString(value, output);
            return this;
        } catch (IOException e) {
            throw new com.meti.api.io.IOException(e);
        }
    }
}
