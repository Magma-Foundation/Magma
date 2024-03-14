package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Collectors;

import static com.meti.None.None;
import static com.meti.Some.Some;

public record Application(Path source) {
    private static String compileLine(String line) {
        var stripped = line.strip();
        if (stripped.startsWith("import ")) {
            var isStatic = stripped.startsWith("import static ");
            var content = stripped.substring(isStatic
                    ? "import static ".length()
                    : "import ".length());

            var last = content.lastIndexOf('.');
            var child = content.substring(last + 1).strip();
            var parent = content.substring(0, last).strip();

            if (child.equals("*")) {
                return "import " +
                       parent +
                       ";\n";
            } else {
                return "import { " +
                       child +
                       " } from " +
                       parent +
                       ";\n";
            }
        } else {
            return "";
        }
    }

    Option<Path> run() throws IOException {
        if (!Files.exists(source)) return None();

        var input = Files.readString(source);
        var root = Arrays.stream(input.split(";"))
                .map(Application::compileLine)
                .collect(Collectors.joining());

        var fileName = source().getFileName().toString();
        var index = fileName.indexOf(".");
        var name = fileName.substring(0, index);
        var target = source().resolveSibling(name + ".mgs");
        Files.writeString(target, root);
        return Some(target);
    }
}