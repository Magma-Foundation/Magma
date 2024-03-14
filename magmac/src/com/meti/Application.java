package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public record Application(Path source) {
    Option<Path> run() throws IOException {
        if (Files.exists(source())) {
            var input = Files.readString(source);
            var lines = Arrays.stream(input.split(";")).toList();
            var output = new StringBuilder();
            for (String line : lines) {
                var stripped = line.strip();
                if (stripped.startsWith("import ")) {
                    var content = stripped.substring("import ".length());
                    var last = content.indexOf('.');
                    var child = content.substring(last + 1).strip();
                    var parent = content.substring(0, last).strip();
                    output.append("import { ")
                            .append(child)
                            .append(" } from ")
                            .append(parent)
                            .append(";\n");
                }
            }

            var fileName = source().getFileName().toString();
            var index = fileName.indexOf(".");
            var name = fileName.substring(0, index);
            var target = source().resolveSibling(name + ".mgs");
            Files.writeString(target, output.toString());
            return Some.Some(target);
        } else {
            return None.None();
        }
    }
}