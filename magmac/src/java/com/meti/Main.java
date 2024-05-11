package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        var source = Paths.get(".", "magmac", "src", "java", "com", "meti", "Main.java");
        try {
            var input = Files.readString(source);
            var target = source.resolveSibling("Main.mgs");
            Files.writeString(target, compile(input));
        } catch (IOException | CompileException e){
            throw new RuntimeException(source.toAbsolutePath().toString(), e);
        }
    }

    private static String compile(String input) throws CompileException {
        throw new CompileException("Unknown input: " + input);
    }

    static class CompileException extends Exception {
        public CompileException(String message) {
            super(message);
        }
    }
}
