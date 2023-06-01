package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.meti.Option.Some;

public record Application(Path source) {
    Option<Path> run() throws IOException {
        if (Files.exists(source)) {
            var input = Files.readString(source);
            var output = new Compiler(input).compile();

            var name = source.getFileName().toString();
            var separator = name.indexOf('.');
            var nameWithoutExtension = name.substring(0, separator);
            var target = source.resolveSibling(nameWithoutExtension + ".mgs");
            Files.writeString(target, output);
            return Some(target);
        } else {
            return Option.None();
        }
    }

}