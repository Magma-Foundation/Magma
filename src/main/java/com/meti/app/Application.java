package com.meti.app;

import com.meti.api.Path;

import java.io.IOException;

public record Application(Path path) {
    public void run() throws IOException {
        path.ensureAsFile();
    }
}
