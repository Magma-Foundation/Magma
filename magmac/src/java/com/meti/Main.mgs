package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        try {
            extracted("java", "mgs");
            extracted("mgs", "js");
            extracted("mgs", "d.ts");
            extracted("mgs", "c");
            extracted("mgs", "h");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void extracted(String sourceExtension, String targetExtension) throws IOException {
        var source = Paths.get(".", "magmac", "src", "java", "com", "meti", "Main." + sourceExtension);
        var input = Files.readString(source);
        Files.writeString(source.resolveSibling("Main." + targetExtension), input);
    }
}