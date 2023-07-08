package com.meti;

import java.nio.file.Files;
import java.nio.file.Path;

public record NIOPath(Path source) {
    boolean isExists() {
        return Files.exists(source);
    }

    JavaString computeFileNameAsString() {
        return new JavaString(source.getFileName().toString());
    }

    Path resolveSibling(JavaString other) {
        return source.resolveSibling(other.unwrap());
    }
}