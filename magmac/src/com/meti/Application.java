package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public record Application(Path source) {

    Optional<Path> run() throws IOException {
        if (Files.exists(source)) {
            var fileName = source.getFileName().toString();
            var separator = fileName.indexOf('.');
            var name = fileName.substring(0, separator);
            var target = source.resolveSibling(name + ".mgs");

            var input = Files.readString(source);
            var output = new Compiler(input).compile();
            Files.writeString(target, output);
            return Optional.of(target);
        } else {
            return Optional.empty();
        }
    }
}