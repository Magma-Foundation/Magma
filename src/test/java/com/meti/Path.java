package com.meti;

import java.io.IOException;
import java.nio.file.Files;

public record Path(java.nio.file.Path path) {
    void ensureDirectory() throws IOException {
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
    }
}
