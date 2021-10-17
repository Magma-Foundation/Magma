package com.meti.java;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public record NIOPath(Path value) implements com.meti.api.Path {
    @Override
    public com.meti.api.Path absolute() {
        return new NIOPath(value.toAbsolutePath());
    }

    @Override
    public void ensureAsFile(String defaultContent) throws com.meti.api.IOException {
        try {
            if (!Files.exists(value)) {
                Files.writeString(value, defaultContent);
            }
        } catch (IOException e) {
            throw new com.meti.api.IOException(e);
        }
    }

    @Override
    public String readContentAsString() throws com.meti.api.IOException {
        try {
            return Files.readString(value);
        } catch (IOException e) {
            throw new com.meti.api.IOException(e);
        }
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
