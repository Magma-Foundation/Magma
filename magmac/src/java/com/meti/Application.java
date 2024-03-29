package com.meti;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Application {
    static void run(SourceSet sourceSet, Path targetDirectory, String targetExtension) throws IOException {
        var set = sourceSet.collect();

        for (var source : set) {
            var namespace = source.findNamespace();
            var without = source.findName();

            var parent = namespace.stream().reduce(targetDirectory, Path::resolve, (path, path2) -> path2);
            if(!Files.exists(parent)) Files.createDirectories(parent);

            var target = parent.resolve(without + targetExtension);
            if (!Files.exists(target)) Files.createFile(target);
        }
    }
}