package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public record NativePath(Path value) {
    Result<NativePath, IOException> createIfNotExists() {
        if (!exists()) {
            try {
                Files.createFile(value);
            } catch (IOException e) {
                return Results.Err(e);
            }
        }

        return Results.Ok(this);
    }

    NativePath resolveSibling(NativeString name) {
        return new NativePath(value.resolveSibling(NativeString.toNative(name)));
    }

    public Path value() {
        return value;
    }

    NativeString asString() {
        return NativeString.fromNative(this.value().toString());
    }

    NativePath getFileName() {
        return new NativePath(value().getFileName());
    }

    boolean exists() {
        return Files.exists(value());
    }

}