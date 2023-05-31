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
        var isWithinString = false;

        for (int i = 0; i < line.length(); i++) {
            var c = line.charAt(i);
            if (c == '}' && depth == 1 && !isWithinString) {
                builder.append("}");
                depth = 0;
                lines.add(builder.toString());
                builder = new StringBuilder();
            } else if (c == ';' && depth == 0 && !isWithinString) {
                lines.add(builder.toString());
                builder = new StringBuilder();
            } else {
                if (!isWithinString) {
                    if (c == '{') depth++;
                    if (c == '}') depth--;
                }
                if (c == '\'' || c == '\"') {
                    isWithinString = !isWithinString;
                }

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
            System.out.println("LINE:" + stripped);

            String output1;
            try {
                output1 = compileNode(stripped);
            } catch (CompileException e) {
                throw new RuntimeException(e);
            }

            output.append(output1);
        }

        var path = Paths.get(".", "Main.mgs");
        writeString(path, output.toString()).match(unused -> null, e -> {
            e.printStackTrace();
            return null;
        });
    }

    private static String compileNode(String stripped) throws CompileException {
        try {
            return compileNodeExceptionally(stripped);
        } catch (CompileException e) {
            throw new CompileException("Failed to compile '" + stripped + "':", e);
        }
    }

    private static String compileNodeExceptionally(String stripped) throws CompileException {
        if (stripped.startsWith("package ")) {
            return "";
        }

        if (stripped.startsWith("else ")) {
            return compileElse(stripped);
        }

        var condition = compileIf(stripped);
        if (condition != null) return condition;

        if (stripped.startsWith("{") && stripped.endsWith("}")) {
            return compileBlock(stripped);
        }

        if (stripped.startsWith("import ")) {
            return compileImport(stripped);
        }

        if (stripped.startsWith("for") && stripped.endsWith("}")) {
            var condStart = stripped.indexOf('(');
            var condEnd = stripped.indexOf(')');
            var condSlice = stripped.substring(condStart + 1, condEnd);
            var bodyStart = stripped.indexOf('{');
            var bodyEnd = stripped.lastIndexOf('}');
            var bodyString = stripped.substring(bodyStart, bodyEnd + 1);
            var body = compileNode(bodyString);
            return "for(" + condSlice + ")" + body;
        }

        var name = compileAssignment(stripped);
        if (name != null) return name;

        var argStart = stripped.indexOf('(');
        var argEnd = stripped.indexOf(')');
        if (argStart != -1 && argEnd != -1 && stripped.endsWith(")")) {
            var leftSlice = stripped.substring(0, argStart);
            var left = compileNode(leftSlice);

            var rightString = slice(stripped, argStart + 1, argEnd);
            var right = compileNode(rightString);

            return left + "(" + right + ")";
        }

        if (stripped.startsWith("\"") && stripped.startsWith("\"")) {
            return stripped;
        }

        if (stripped.contains("class ")) {
            return compileClass(stripped);
        }

        var paramStart = stripped.indexOf('(');
        var paramEnd = stripped.indexOf(')');
        if (paramStart != -1 && paramEnd != -1 && paramStart < paramEnd) {
            var args1 = List.of(slice(stripped, paramStart).split(" "));
            var name1 = args1.get(args1.size() - 1);
            var bodyStart = stripped.indexOf('{');
            var bodyEnd = stripped.indexOf('}');
            var bodyString = slice(stripped, bodyStart, bodyEnd + 1).strip();
            var output = compileNode(bodyString);

            return "public def " + name1 + "(args : NativeArray[NativeString]) => " + output;
        }

        return stripped;
    }

    private static String compileAssignment(String stripped) throws CompileException {
        var equals = stripped.indexOf('=');
        if (equals != -1) {
            var left = stripped.substring(0, equals).strip();
            var space = left.indexOf(' ');
            var name = left.substring(space + 1);

            var rightString = stripped.substring(equals + 1).strip();
            var right = compileNode(rightString);
            return "let " + name + "=" + right;
        }
        return null;
    }

    private static String compileIf(String stripped) throws CompileException {
        var start = stripped.indexOf('(');
        var end = stripped.indexOf(')');
        if (stripped.startsWith("if") && start != -1 && end != -1) {
            var conditionString = slice(stripped, start + 1, end);
            var condition = compileNode(conditionString);

            var bodyString = slice(stripped, stripped.indexOf('{'), stripped.indexOf("}") + 1);
            var body = compileNode(bodyString);

            return "if " + condition + " " + body;
        }
        return null;
    }

    private static String slice(String stripped, int start, int end) throws CompileException {
        try {
            return stripped.substring(start, end);
        } catch (Exception e) {
            throw new CompileException(start + " " + end + ": " + stripped);
        }
    }

    private static String compileClass(String stripped) throws CompileException {
        var index = stripped.indexOf("class ");
        var keywords = slice(stripped, index).strip();
        var nameStart = index + "class ".length();
        var nameEnd = stripped.indexOf('{');

        String name;
        try {
            name = slice(stripped, nameStart, nameEnd).strip();
        } catch (Exception e) {
            var format = "%d %d: '%s'";
            var message = format.formatted(nameStart, nameEnd, stripped);
            throw new IndexOutOfBoundsException(message);
        }

        var block = slice(stripped, nameEnd, stripped.lastIndexOf('}') + 1);
        var blockOutput = compileNode(block);

        return keywords +
               " object " +
               name +
               " " + blockOutput;
    }

    private static String slice(String stripped, int index) {
        try {
            return slice(stripped, 0, index);
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

    private static String compileBlock(String stripped) throws CompileException {
        var sliced = slice(stripped, 1, stripped.length() - 1);
        var lines = split(sliced);
        var compiled = new ArrayList<String>();
        for (String line : lines) {
            compiled.add(compileNode(line.strip()));
        }
        return "{\n\t" + String.join("", compiled) + "}";
    }

    private static String compileElse(String stripped) throws CompileException {
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

    static class CompileException extends Exception {
        public CompileException(String message) {
            super(message);
        }

        public CompileException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
