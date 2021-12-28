package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class NIOPath implements com.meti.Path {
    static final com.meti.Path Root = new NIOPath(Paths.get("."));
    private final Path target;

    public NIOPath(Path target) {
        this.target = target;
    }

    @Override
    public void create() throws com.meti.IOException {
        try {
            Files.createFile(target);
        } catch (IOException e) {
            throw new com.meti.IOException(e);
        }
    }

    @Override
    public void deleteIfExists() throws com.meti.IOException {
        try {
            Files.deleteIfExists(target);
        } catch (IOException e) {
            throw new com.meti.IOException(e);
        }
    }

    @Override
    public boolean exists() {
        return Files.exists(target);
    }

    @Override
    public com.meti.Path resolve(String child) {
        return new NIOPath(target.resolve(child));
    }
}
