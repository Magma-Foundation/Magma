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
        } else if (stripped.startsWith("else ")) {
            return compileElse(stripped);
        } else if (stripped.startsWith("if")) {
            var start = stripped.indexOf('(');
            var end = stripped.indexOf(')');

            var conditionString = stripped.substring(start + 1, end);
            var condition = compileNode(conditionString);

            var bodyString = stripped.substring(stripped.indexOf('{'), stripped.indexOf("}") + 1);
            var body = compileNode(bodyString);

            return "if " + condition + " " + body;
        } else if (stripped.startsWith("{") && stripped.endsWith("}")) {
            return compileBlock(stripped);
        } else if (stripped.startsWith("import ")) {
            return compileImport(stripped);
        } else if (stripped.contains("class ")) {
            return compileClass(stripped);
        } else if (stripped.contains("(")) {
            return compileMethod(stripped);
        } else {
            return stripped;
        }
    }

    private static String compileMethod(String stripped) {
        var paramStart = stripped.indexOf('(');
        var args1 = List.of(slice(stripped, paramStart).split(" "));
        var name = args1.get(args1.size() - 1);
        return "public def " + name + "(args : NativeArray[NativeString]) => {\n\t\tmatch Files.writeString(Paths.get(\".\", \"Main.mgs\"), \"\") {\n" +
               "            Ok => {},\n" +
               "            Err(e : IOException) => e.printStackTrace()\n" +
               "        }\n\t}\n";
    }

    private static String compileClass(String stripped) {
        var index = stripped.indexOf("class ");
        var keywords = slice(stripped, index).strip();
        var nameStart = index + "class ".length();
        var nameEnd = stripped.indexOf('{');

        String name;
        try {
            name = stripped.substring(nameStart, nameEnd).strip();
        } catch (Exception e) {
            var format = "%d %d: '%s'";
            var message = format.formatted(nameStart, nameEnd, stripped);
            throw new IndexOutOfBoundsException(message);
        }

        var block = stripped.substring(nameEnd, stripped.lastIndexOf('}') + 1);
        var blockOutput = compileNode(block);

        return keywords +
               " object " +
               name +
               " " + blockOutput;
    }

    private static String slice(String stripped, int index) {
        try {
            return stripped.substring(0, index);
        } catch (Exception e) {
            throw new IndexOutOfBoundsException(index + ": " + stripped);
        }
    }

    private static String compileImport(String stripped) {
        var name = stripped.substring("import ".length());
        var nameSegment = List.of(name.split("\\."));
        var joinedNames = String.join(".", nameSegment.subList(0, nameSegment.size() - 1));

        return "import " +
               nameSegment.get(nameSegment.size() - 1) +
               " from " +
               joinedNames +
               ";\n";
    }

    private static String compileBlock(String stripped) {
        var sliced = stripped.substring(1, stripped.length() - 1);
        var lines = split(sliced);
        var compiled = new ArrayList<String>();
        for (String line : lines) {
            compiled.add(compileNode(line));
        }
        return "{\n\t" + String.join("", compiled) + "}";
    }

    private static String compileElse(String stripped) {
        var withoutElse = stripped.substring("else ".length());
        var withoutElseRendered = compileNode(withoutElse);
        return "else " + withoutElseRendered;
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
