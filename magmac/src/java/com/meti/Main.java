package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        try {
            var source = Paths.get("./src/java/com/meti/CompileException.java");
            var output = JavaToMagmaCompiler.run(Files.readString(source));
            Files.writeString(source.resolveSibling("CompileException.mgs"), output);
        } catch (CompileException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
