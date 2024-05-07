package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        try {
            var source = Paths.get(".", "src", "java", "com", "meti", "Main.java");
            var target = source.resolveSibling("Main.mgs");
            var input = Files.readString(source);
            var lines = new ArrayList<String>();
            var buffer = new StringBuilder();
            var depth = 0;
            for (int i = 0; i < input.length(); i++) {
                var c = input.charAt(i);
                if(c == ';' && depth == 0 ){
                    lines.add(buffer.toString());
                    buffer = new StringBuilder();
                } else {
                    if(c == '{') depth++;
                    if(c == '}') depth--;
                    buffer.append(c);
                }
            }
            lines.add(buffer.toString());

            var outputLines = new ArrayList<String>();
            for (var line : lines) {
                var stripped = line.strip();
                if(!stripped.startsWith("package ")) {
                    outputLines.add(stripped);
                }
            }

            var output = String.join("", outputLines);
            Files.writeString(target, output);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
