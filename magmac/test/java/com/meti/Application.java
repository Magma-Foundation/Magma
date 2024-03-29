package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Application {
    static void run(SourceSet sourceSet, Path targetDirectory, String targetExtension) throws IOException {
        var set = sourceSet.collect();

        for (var source : set) {
            source.findNamespace();
            var without = source.findName();

            var target = targetDirectory.resolve(without + targetExtension);
            if (!Files.exists(target)) Files.createFile(target);
        }
    }
}