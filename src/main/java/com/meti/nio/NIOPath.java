package com.meti.nio;

import com.meti.core.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class NIOPath extends AbstractNIOLocation {

    public NIOPath(Path location) {
        super(location);
    }

    public static NIOPath from(NIOLocation value) {
        return new NIOPath(value.unwrap());
    }

    boolean isExists() {
        return Files.exists(location);
    }

    public Result<NIOFile, IOException> ensureAsFile() {
        return Results.asResult(() -> {
            if (!isExists()) {
                Files.createFile(location);
            }
            return new NIOFile(location);
        });
    }

    public Option<NIOFile> existsAsFile() {
        if (Files.exists(location)) {
            return Some.apply(new NIOFile(location));
        } else {
            return new None<>();
        }
    }
}