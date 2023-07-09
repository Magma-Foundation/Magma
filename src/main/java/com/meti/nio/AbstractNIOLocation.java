package com.meti.nio;

import com.meti.java.JavaString;

import java.nio.file.Path;

public class AbstractNIOLocation implements NIOLocation {
    protected final Path location;

    public AbstractNIOLocation(Path location) {
        this.location = location;
    }

    @Override
    public JavaString computeFileNameAsString() {
        return new JavaString(location.getFileName().toString());
    }

    @Override
    public NIOPath resolveSibling(JavaString other) {
        return new NIOPath(location.resolveSibling(other.unwrap()));
    }

    @Override
    public Path unwrap() {
        return location;
    }
}
