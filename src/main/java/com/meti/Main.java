package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Main {
    public static List<String> split(String line) {
        var lines = new ArrayList<String>();
        var builder = new StringBuilder();
        var depth = 0;
        for (int i = 0; i < line.length(); i++) {
            var c = line.charAt(i);
            if (c == '}' && depth == 1) {
                builder.append("}");
                depth = 0;
                lines.add(builder.toString());
                builder = new StringBuilder();
            } else if (c == ';' && depth == 0) {
                lines.add(builder.toString());
                builder = new StringBuilder();
            } else {
                if (c == '{') depth++;
                if (c == '}') depth--;
                builder.append(c);
            }
        }

        lines.add(builder.toString());
        lines.removeIf(String::isBlank);
        return lines;
    }

    public static void main(String[] args) {
        var input = readStringImpl(Paths.get(".", "Main.java")).match(value -> value, err -> {
            err.printStackTrace();
            return "";
        });

        var lines = split(input);
        var output = new StringBuilder();
        for (var line : lines) {
            var stripped = line.strip();
            if (stripped.startsWith("package ")) {
            } else if (stripped.startsWith("import ")) {
                var name = stripped.substring("import ".length());
                var nameSegment = List.of(name.split("\\."));
                var joinedNames = String.join(".", nameSegment.subList(0, nameSegment.size() - 1));
                output.append("import ")
                        .append(nameSegment.get(nameSegment.size() - 1))
                        .append(" from ")
                        .append(joinedNames)
                        .append(";\n");
            } else if (stripped.contains("class")) {
                var index = stripped.indexOf("class ");
                var keywords = stripped.substring(0, index).strip();
                var name = stripped.substring(index + "class ".length(), stripped.indexOf('{')).strip();

                output.append(keywords)
                        .append(" ")
                        .append("class def ")
                        .append(name)
                        .append("(){\n}");
            }
        }

        var path = Paths.get(".", "Main.mgs");
        writeString(path, output.toString()).match(unused -> null, e -> {
            e.printStackTrace();
            return null;
        });
    }

    private static Result<Void> writeString(Path path, String output) {
        try {
            Files.writeString(path, output);
            return Result.ok();
        } catch (IOException e) {
            return Result.err(e);
        }
    }

    private static Result<String> readStringImpl(Path path) {
        try {
            return Result.ok(Files.readString(path));
        } catch (IOException e) {
            return Result.err(e);
        }
    }

    private interface Result<T> {
        static <T> Result<T> err(IOException value) {
            return new Err<>(value);
        }

        static <T> Result<T> ok(T value) {
            return new Ok<>(value);
        }

        static Result<Void> ok() {
            return Result.ok(null);
        }

        T match(Function<T, T> onOk, Function<IOException, T> onErr);

        record Err<T>(IOException value) implements Result<T> {

            @Override
            public T match(Function<T, T> onOk, Function<IOException, T> onErr) {
                return onErr.apply(value);
            }
        }

        record Ok<T>(T value) implements Result<T> {
            @Override
            public T match(Function<T, T> onOk, Function<IOException, T> onErr) {
                return onOk.apply(value);
            }
        }
    }
}
