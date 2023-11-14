package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public record Application(Path source) {
    private static String compile(String input) {
        String output;
        if (input.isEmpty()) {
            output = "";
        } else {
            var slice = input.substring("import ".length());
            var importSeparator = slice.indexOf('.');
            var parent = slice.substring(0, importSeparator);
            var child = slice.substring(importSeparator + 1);
            output = "import { " + child + " } from " + parent + ";";
        }
        return output;
    }

    Optional<Path> run() throws IOException {
        if (Files.exists(source)) {
            var fileName = source.getFileName().toString();
            var separator = fileName.indexOf('.');
            var name = fileName.substring(0, separator);
            var target = source.resolveSibling(name + ".mgs");

            var input = Files.readString(source);
            var output = compile(input);
            Files.writeString(target, output);
            return Optional.of(target);
        } else {
            return Optional.empty();
        }
    }
}