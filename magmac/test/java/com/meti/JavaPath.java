package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public record JavaPath(Path target) {
    Result<Boolean, com.meti.java.IOException> deleteIfExists() {
        try {
            return new Ok<>(Files.deleteIfExists(target()));
        } catch (IOException e) {
            return new Err<>(new com.meti.java.IOException(e));
        }
    }
}