package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        try {
            compile("java", "mgs");
            compile("mgs", "js");
            compile("mgs", "d.ts");
            compile("mgs", "c");
            compile("mgs", "h");
        } catch (IOException | CompileException e) {
            throw new RuntimeException(e);
        }
    }

    private static void compile(String sourceEnv, String targetEnv) throws IOException, CompileException {
        var source = Paths.get(".", "magmac", "src", "java", "com", "meti", "Main." + sourceEnv);
        var input = Files.readString(source);
        var target = source.resolveSibling("Main." + targetEnv);
        Files.writeString(target, compile(input));
    }

    private static String compile(String input) throws CompileException {
        throw new CompileException(input);
    }
}
