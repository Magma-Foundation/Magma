package com.meti;

import java.nio.file.Path;

public class TargetSet {
    private final Path header;
    private final Path target;

    public TargetSet(Path header, Path target) {
        this.header = header;
        this.target = target;
    }

    public Path getHeader() {
        return header;
    }

    public Path getTarget() {
        return target;
    }
}
