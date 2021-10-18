package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Application {
    public static final Path Source = Paths.get(".", "index.mgf");

    void run() throws IOException {
        if (!Files.exists(Source)) Files.createFile(Source);
        var input = Files.readString(Source);
        var output = new Compiler(input).compile();
        Files.writeString(Source, output);
    }
}