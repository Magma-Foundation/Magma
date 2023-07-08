package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public record NIOPath(Path location) {
    public static NIOPath from(NIOFile value) {
        return new NIOPath(value.unwrap());
    }

    boolean isExists() {
        return Files.exists(location);
    }

    JavaString computeFileNameAsString() {
        return new JavaString(location.getFileName().toString());
    }

    NIOPath resolveSibling(JavaString other) {
        return new NIOPath(location.resolveSibling(other.unwrap()));
    }

    public Result<NIOFile, IOException> ensureAsFile() {
        return Results.asResult(() -> {
            if (isExists()) {
                Files.createFile(location);
            }
            return new NIOFile(location);
        });
    }
}