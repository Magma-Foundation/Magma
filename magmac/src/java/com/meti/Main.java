package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        var source = Paths.get(".", "magmac", "src", "java", "com", "meti", "Main.java");
        try {
            var input = Files.readString(source);
            var target = source.resolveSibling("Main.mgs");

            var inputContent = split(input);
            var outputContent = new ArrayList<String>();
            for (String line : inputContent) {
                var stripped = line.strip();
                if(stripped.isEmpty() ||stripped.startsWith("package ")) continue;
                if (stripped.startsWith("import ")) {
                    var segments = stripped.substring("import ".length());
                    var separator = segments.lastIndexOf('.');
                    var parent = segments.substring(0, separator);
                    var child = segments.substring(separator + 1);
                    outputContent.add("import { " + child + " } from " + parent + ";\n");
                } else {
                    outputContent.add(stripped);
                }
            }

            var output = String.join("", outputContent);
            Files.writeString(target, output);
        } catch (IOException e) {
            throw new RuntimeException(source.toAbsolutePath().toString(), e);
        }
    }

    private static ArrayList<String> split(String input) {
        var lines = new ArrayList<String>();
        var builder = new StringBuilder();
        var depth = 0;
        for (int i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            if (c == ';' && depth == 0) {
                lines.add(builder.toString());
                builder = new StringBuilder();
            } else {
                if (c == '{') depth++;
                if (c == '}') depth--;
                builder.append(c);
            }
        }
        lines.add(builder.toString());
        return lines;
    }
}
