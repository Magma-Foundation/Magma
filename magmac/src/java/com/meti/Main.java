package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        try {
            run(Paths.get("./Personal/Magma/magmac/test/java/com/meti/ApplicationTest.java"));
        } catch (IOException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }

    static void run(Path source) throws IOException {
        if (!Files.exists(source)) return;

        var input = Files.readString(source);
        var output = Compiler.compile(input);

        var fileName = source.getFileName().toString();
        var separator = fileName.indexOf('.');
        var fileNameWithoutExtension = fileName.substring(0, separator);
        Files.writeString(source.resolveSibling(fileNameWithoutExtension + ".mgs"), output);
    }
}
