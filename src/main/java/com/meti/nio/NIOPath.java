package com.meti.nio;

import com.meti.core.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class NIOPath extends NIOLocation {

    public NIOPath(Path location) {
        super(location);
    }

    public static NIOPath from(Location value) {
        return new NIOPath(value.unwrap());
    }

    boolean isExists() {
        return Files.exists(value);
    }

    public Result<NIOFile, IOException> ensureAsFile() {
        return Results.asResult(() -> {
            if (!isExists()) {
                Files.createFile(value);
            }
            return new NIOFile(value);
        });
    }

    public Option<NIOFile> existsAsFile() {
        if (Files.exists(value)) {
            return Some.apply(new NIOFile(value));
        } else {
            return new None<>();
        }
    }

    public Result<NIODirectory, IOException> ensureAsDirectory() {
        return Results.asResult(() -> {
            if (!Files.exists(value)) {
                Files.createDirectories(value);
            }

            return new NIODirectory(value);
        });
    }
}