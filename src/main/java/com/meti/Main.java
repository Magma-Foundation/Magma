package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Function;

public class Main {
    public static void main(String[] args) {
        var input = readStringImpl(Paths.get(".", "Main.java")).match(value -> value, err -> {
            err.printStackTrace();
            return "";
        });

        var lines = input.split(";");
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
