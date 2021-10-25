package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public record Application(Path source) {
    Path run() throws IOException {
        if (Files.exists(source())) Files.createFile(ApplicationTest.Target);
        return ApplicationTest.Target;
    }
}