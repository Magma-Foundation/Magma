package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        try {
            run(new DirectorySourceSet(Paths.get("./Personal/Magma/magmac/test/java")));
        } catch (IOException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }

    static void run(SourceSet sourceSet) throws IOException {
        var set = sourceSet.collect();

        for (var source1 : set) {
            var input = Files.readString(source1);
            var output = Compiler.compile(input);

            var fileName = source1.getFileName().toString();
            var separator = fileName.indexOf('.');
            var fileNameWithoutExtension = fileName.substring(0, separator);
            Files.writeString(source1.resolveSibling(fileNameWithoutExtension + ".mgs"), output);
        }
    }
}
