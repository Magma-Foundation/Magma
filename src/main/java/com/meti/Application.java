package com.meti;

import java.io.IOException;

public record Application(Path path) {
    void run() throws IOException {
        path.ensureAsFile();
    }
}
