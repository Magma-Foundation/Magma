package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        var source = Paths.get(".", "test", "java", "com", "meti", "ApplicationTest.java");
        try {
            var sourceContent = Files.readString(source);

            var targetContent = Compiler.run(sourceContent);
            var target = source.resolveSibling("ApplicationTest.mgs");
            Files.writeString(target, targetContent);
        } catch (IOException e) {
            throw new RuntimeException(source.toAbsolutePath().toString(), e);
        }
    }
}
