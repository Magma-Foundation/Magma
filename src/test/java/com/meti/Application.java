package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public record Application(Path source) {

    Path run() throws IOException {
        var fileName = source.getFileName().toString();
        var separator = fileName.indexOf('.');
        var name = fileName.substring(0, separator);
        var targetName = name + ".c";
        var target = source.resolveSibling(targetName);
        if (Files.exists(source)) {
            var input = Files.readString(source);
            var output = new Compiler(input).getString();
            Files.writeString(target, output);
        }

        return target;
    }
}