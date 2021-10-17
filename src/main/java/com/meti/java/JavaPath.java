package com.meti.java;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public record JavaPath(Path value) implements com.meti.api.Path {
    @Override
    public void ensureAsFile() throws IOException {
        if (!Files.exists(value)) {
            Files.createFile(value);
        }
    }
}
