package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.meti.None.None;
import static com.meti.Some.Some;

public record Application(Path source) {
    private static String compileLine(String line) {
        var stripped = line.strip();
        if (stripped.startsWith("public class ")) {
            var start = stripped.indexOf("{");
            var end = stripped.lastIndexOf("}");

            var name = stripped.substring("public class ".length(), start).strip();
            var content = stripped.substring(start);

            return "export class def " + name + "() => " + content;
        } else if (stripped.startsWith("import ")) {
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

    private static Stream<String> split(String input) {
        var lines = new ArrayList<String>();
        var builder = new StringBuilder();
        var depth = 0;
        for (int i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            if (c == ';' && depth == 0) {
                lines.add(builder.toString());
                builder = new StringBuilder();
            } else {
                if(c == '{') depth++;
                if(c == '}') depth--;
                builder.append(c);
            }
        }
        lines.add(builder.toString());
        return lines.stream();
    }

    Option<Path> run() throws IOException {
        if (!Files.exists(source)) return None();

        var input = Files.readString(source);
        var root = split(input)
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