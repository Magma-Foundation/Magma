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
            var output1 = compileNode(stripped);
            output.append(output1);
        }

        var path = Paths.get(".", "Main.mgs");
        writeString(path, output.toString()).match(unused -> null, e -> {
            e.printStackTrace();
            return null;
        });
    }

    private static String compileNode(String stripped) {
        if (stripped.startsWith("package ")) {
            return "";
        } else if (stripped.startsWith("{") && stripped.endsWith("}")) {
            var sliced = stripped.substring(1, stripped.length() - 1);
            var lines = split(sliced);
            var compiled = new ArrayList<String>();
            for (String line : lines) {
                compiled.add(compileNode(line));
            }
            return "{\n\t" + String.join("", compiled) + "}";
        } else if (stripped.startsWith("import ")) {
            var name = stripped.substring("import ".length());
            var nameSegment = List.of(name.split("\\."));
            var joinedNames = String.join(".", nameSegment.subList(0, nameSegment.size() - 1));

            return "import " +
                   nameSegment.get(nameSegment.size() - 1) +
                   " from " +
                   joinedNames +
                   ";\n";
        } else if (stripped.contains("class")) {
            var index = stripped.indexOf("class ");
            var keywords = stripped.substring(0, index).strip();
            var name = stripped.substring(index + "class ".length(), stripped.indexOf('{')).strip();
            var block = stripped.substring(stripped.indexOf('{'), stripped.lastIndexOf('}') + 1);
            var blockOutput = compileNode(block);

            return keywords +
                   " object " +
                   name +
                   " " + blockOutput;
        } else if (stripped.contains("(")) {
            var paramStart = stripped.indexOf('(');
            var args1 = List.of(stripped.substring(0, paramStart).split(" "));
            var name = args1.get(args1.size() - 1);
            return "public def " + name + "(args : NativeArray[NativeString]) => {\n\t\tmatch Files.writeString(Paths.get(\".\", \"Main.mgs\"), \"\") {\n" +
                   "            Ok => {},\n" +
                   "            Err(e : IOException) => e.printStackTrace()\n" +
                   "        }\n\t}\n";
        } else {
            throw new IllegalStateException(stripped);
        }
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
