package com.meti;

import java.io.IOException;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        var sourcePath = Paths.get(".", "Main.java");
        var source = new VolatileSingleSource(sourcePath);
        try {
            new Application(source).runExceptionally();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
