package com.meti;

import java.nio.file.Path;

public class NIOFile {
    private final Path location;

    public NIOFile(Path location) {
        this.location = location;
    }

    public Path unwrap() {
        return location;
    }
}
