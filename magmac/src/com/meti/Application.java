package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public record Application(Path source) {
    Option<Path> run() throws IOException {
        if (Files.exists(source())) {
            var fileName = source().getFileName().toString();
            var index = fileName.indexOf(".");
            var name = fileName.substring(0, index);
            var target = source().resolveSibling(name + ".mgs");
            Files.createFile(target);
            return Some.Some(target);
        } else {
            return None.None();
        }
    }
}