package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        try {
            var source = Paths.get(".", "src", "java", "com", "meti", "Main.java");
            var input = Files.readString(source);
            Files.writeString(source.resolve("Main.mgs"), input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
