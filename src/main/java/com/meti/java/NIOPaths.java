package com.meti.java;

import com.meti.api.Path;

import java.nio.file.Paths;

public class NIOPaths {
    public static Path Root() {
        return new NIOPath(Paths.get("."));
    }
}
