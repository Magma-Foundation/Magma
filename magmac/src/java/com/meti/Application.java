package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public record Application(SourceSet sourceSet) {
    void run() throws IOException {
        var set = sourceSet().collect();

        for (Path path : set) {
            var name = path.getFileName().toString();
            var separator = name.indexOf('.');
            var withoutExtension = name.substring(0, separator);
            Files.createFile(path.resolveSibling(withoutExtension + ".mgs"));
        }
    }
}