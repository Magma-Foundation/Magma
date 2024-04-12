package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        var source = Paths.get(".", "test", "java", "com", "meti", "CompiledTest.java");
        try {
            var input = Files.readString(source);
            var output = Application.compileJavaToMagma(input);
            Files.writeString(source.resolveSibling("CompiledTest.mgs"), output);
        } catch (IOException | CompileException e) {
            throw new RuntimeException("Failed to compile file: " + source.toAbsolutePath(), e);
        }
    }
}
