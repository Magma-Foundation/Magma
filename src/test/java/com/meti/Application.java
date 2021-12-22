package com.meti;

import java.io.IOException;

public class Application {
    private final FileWrapper source;

    public Application(FileWrapper source) {
        this.source = source;
    }

    Option<FileWrapper> run() throws IOException {
        if (source.exists()) {
            var name = source.computeRetractedFileName();
            var target = source.resolveSibling(name + ".c");
            return new Some<>(target.create());
        }
        return new None<>();
    }
}
