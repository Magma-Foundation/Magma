package jvm.api.io;

import magma.api.io.Path_;

public class Paths {
    public static Path_ get(String first, String... more) {
        return new JavaPath(java.nio.file.Paths.get(first, more));
    }
}
