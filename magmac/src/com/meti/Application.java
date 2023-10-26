package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public record Application(Path source) {
    Optional<Path> run() throws CompileException {
        if (Files.exists(source())) {
            return compileSource();
        }
        return Optional.empty();
    }

    private Optional<Path> compileSource() throws CompileException {
        String input;
        try {
            input = Files.readString(source);
        } catch (IOException e) {
            throw new CompileException("Failed to read source.", e);
        }

        String output;
        if (input.startsWith("import ")) {
            var separator = input.indexOf('.');
            if (separator == -1) {
                throw new CompileException("Invalid import syntax.");
            } else {
                var parent = input.substring("import ".length(), separator).strip();
                var child = input.substring(separator + 1).strip();
                output = "import { %s } from %s;".formatted(child, parent);
            }
        } else {
            output = "";
        }

        var fileName = source().getFileName().toString();
        var separator = fileName.indexOf('.');
        var fileNameWithoutExtension = separator == -1 ? fileName : fileName.substring(0, separator);
        var target = source().resolveSibling(fileNameWithoutExtension + ".mgs");
        try {
            Files.writeString(target, output);
            return Optional.of(target);
        } catch (IOException e) {
            throw new CompileException("Failed to write target.", e);
        }
    }
}