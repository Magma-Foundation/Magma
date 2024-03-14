package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.meti.None.None;
import static com.meti.Some.Some;

public record Application(Path source) {
    private static String compileLine(String line, int indent) {
        var stripped = line.strip();
        if (stripped.startsWith("\"") && stripped.endsWith("\"")) {
            return "\"" + stripped.substring(1, stripped.length() - 1) + "\"";
        } else if (stripped.startsWith("public static final Path ")) {
            var content = stripped.substring("public static final Path ".length());
            var equals = content.indexOf('=');
            var name = content.substring(0, equals).strip();
            var compiledValue = compileLine(content.substring(equals + 1), 0);
            return "\t".repeat(indent) + "pub const " + name + " = " + compiledValue + ";\n";
        } else if (stripped.startsWith("{") && stripped.endsWith("}")) {
            return split(stripped.substring(1, stripped.length() - 1).strip())
                    .map(line1 -> compileLine(line1, 1))
                    .collect(Collectors.joining("", "{\n", "}"));
        } else if (stripped.startsWith("public class ")) {
            var start = stripped.indexOf("{");

            var name = stripped.substring("public class ".length(), start).strip();
            var content = stripped.substring(start);
            var contentOutput = compileLine(content, 0);

            return "export object " + name + " = " + contentOutput;
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
        } else if (stripped.startsWith("Paths.get(")) {
            var start = stripped.indexOf("(");
            var end = stripped.indexOf(")");
            var args = Arrays.stream(stripped.substring(start + 1, end)
                            .split(","))
                    .map(arg -> compileLine(arg, 0))
                    .collect(Collectors.joining(", ", "(", ")"));
            return "Paths.get" + args;
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
            } else if(c == '}' && depth == 1) {
                builder.append("}");
                depth--;
                lines.add(builder.toString());
                builder = new StringBuilder();
            } else {
                if (c == '{') depth++;
                if (c == '}') depth--;
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
                .map(line -> compileLine(line, 0))
                .collect(Collectors.joining());

        var fileName = source().getFileName().toString();
        var index = fileName.indexOf(".");
        var name = fileName.substring(0, index);
        var target = source().resolveSibling(name + ".mgs");
        Files.writeString(target, root);
        return Some(target);
    }
}