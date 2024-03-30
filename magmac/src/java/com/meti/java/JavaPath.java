package com.meti.java;

import com.meti.core.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public record JavaPath(Path value) {
    public static final JavaPath CURRENT_WORKING_DIRECTORY = new JavaPath(Paths.get("."));

    public Path resolveChild(String name) {
        return value.resolve(name);
    }

    public Option<com.meti.io.IOException> createFile() {
        try {
            Files.createFile(value());
            return new None<>();
        } catch (IOException e) {
            return new Some<>(new com.meti.io.IOException(e));
        }
    }

    public boolean exists() {
        return Files.exists(value());
    }

    public Result<Boolean, com.meti.io.IOException> deleteIfExists() {
        try {
            return new Ok<>(Files.deleteIfExists(value()));
        } catch (IOException e) {
            return new Err<>(new com.meti.io.IOException(e));
        }
    }
}