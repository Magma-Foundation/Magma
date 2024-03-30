package com.meti.java;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JavaApplication {
    public static final Path ROOT = Paths.get(".");
    public static final Path TARGET = ROOT.resolve("ApplicationTest.mgs");
    public static final Path SOURCE = ROOT.resolve("ApplicationTest.java");

    public static boolean exists() {
        return Files.exists(TARGET);
    }

    public static Option<com.meti.java.IOException> createSource() {
        try {
            Files.createFile(SOURCE);
            return new None<>();
        } catch (IOException e) {
            return new Some<>(new com.meti.java.IOException(e));
        }
    }

    public static Option<com.meti.java.IOException> run() {
        try {
            if (Files.exists(SOURCE)) {
                Files.createFile(TARGET);
            }
            return new None<>();
        } catch (IOException e) {
            return new Some<>(new com.meti.java.IOException(e));
        }
    }
}