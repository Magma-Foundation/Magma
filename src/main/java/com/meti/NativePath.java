package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public record NativePath(Path unwrap) {
    Result<NativePath, IOException> createIfNotExists() {
        if (!exists()) {
            try {
                Files.createFile(unwrap);
            } catch (IOException e) {
                return Results.Err(e);
            }
        }

        return Results.Ok(this);
    }

    NativePath resolveSibling(NativeString name) {
        return new NativePath(unwrap.resolveSibling(NativeString.toNative(name)));
    }

    public Path unwrap() {
        return unwrap;
    }

    NativeString asString() {
        return NativeString.fromNative(this.unwrap.toString());
    }

    NativePath getFileName() {
        return new NativePath(unwrap.getFileName());
    }

    boolean exists() {
        return Files.exists(unwrap);
    }
}