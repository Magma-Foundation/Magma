package jvm.io;

import magma.io.Path_;

import java.nio.file.Path;

public class Paths {
    public static Path_ get(String first, String... more) {
        return new NIOPath(java.nio.file.Paths.get(first, more));
    }

    public static Path toNative(Path_ path) {
        return path.stream().foldMapping(java.nio.file.Paths::get, Path::resolve).orElse(java.nio.file.Paths.get("."));
    }
}
