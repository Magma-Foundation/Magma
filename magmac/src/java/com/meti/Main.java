package com.meti;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        try {
            var javaSource = Paths.get(".", "magmac", "src", "java").toAbsolutePath();
            var javaTest = Paths.get(".", "magmac", "test", "java").toAbsolutePath();

            var magmaSource = Paths.get(".", "magmac", "src", "magma").toAbsolutePath();
            var magmaTest = Paths.get(".", "magmac", "test", "magma").toAbsolutePath();

            runImpl(javaSource, magmaSource);
            runImpl(javaTest, magmaTest);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void runImpl(Path javaSource, Path magmaSource) throws IOException {
        new Application(new DirectorySourceSet(javaSource), magmaSource).run();
    }
}
