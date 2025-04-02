package magma;

import magma.result.Err;
import magma.result.Ok;
import magma.result.Result;
import magma.result.Results;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.function.Function;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        try {
            Path source = Paths.get(".", "src", "java", "magma", "Main.java");
            String input = Files.readString(source);
            String string = Results.unwrap(compileRoot(input));
            Path target = source.resolveSibling("Main.c");
            Files.writeString(target, string);
        } catch (IOException | CompileException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }

    private static Result<String, CompileException> compileRoot(String input) throws CompileException {
        return divideAndCompile(input, Main::compileRootSegment);
    }

    private static Result<String, CompileException> divideAndCompile(String input, Function<String, Result<String, CompileException>> compiler) {
        ArrayList<String> segments = new ArrayList<>();
        StringBuilder buffer = new StringBuilder();
        int depth = 0;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            buffer.append(c);
            if (c == ';' && depth == 0) {
                segments.add(buffer.toString());
                buffer = new StringBuilder();
            } else if (c == '}' && depth == 1) {
                segments.add(buffer.toString());
                buffer = new StringBuilder();
                depth--;
            } else {
                if (c == '{') depth++;
                if (c == '}') depth--;
            }
        }
        segments.add(buffer.toString());

        Result<StringBuilder, CompileException> output = new Ok<>(new StringBuilder());
        for (String segment : segments) {
            output = output.and(() -> compiler.apply(segment))
                    .mapValue(tuple -> tuple.left().append(tuple.right()));
        }

        return output.mapValue(StringBuilder::toString);
    }

    private static Result<String, CompileException> compileRootSegment(String input) {
        if (input.startsWith("package ")) return new Ok<>("");
        String stripped = input.strip();
        if (stripped.startsWith("import ")) {
            String right = stripped.substring("import ".length());
            if (right.endsWith(";")) {
                String content = right.substring(0, right.length() - ";".length());
                String[] segments = content.split(Pattern.quote("."));
                String joined = String.join("/", segments);
                return new Ok<>("#include \"" + joined + ".h\"\n");
            }
        }

        int classIndex = input.indexOf("class ");
        if (classIndex >= 0) {
            String afterKeyword = input.substring(classIndex + "class ".length());
            int contentStart = afterKeyword.indexOf("{");
            if (contentStart >= 0) {
                String name = afterKeyword.substring(0, contentStart).strip();
                String withEnd = afterKeyword.substring(contentStart + "{".length()).strip();
                if (withEnd.endsWith("}")) {
                    String inputContent = withEnd.substring(0, withEnd.length() - "}".length());
                    return divideAndCompile(inputContent, Main::compileClassSegment).mapValue(outputContent -> {
                        return "struct " + name + " {\n};\n" + outputContent;
                    });
                }
            }
        }

        return invalidate(input, "root segment");
    }

    private static Err<String, CompileException> invalidate(String input, String type) {
        return new Err<>(new CompileException(input, "Invalid " + type));
    }

    private static Result<String, CompileException> compileClassSegment(String classSegment) {
        return invalidate(classSegment, "class segment");
    }
}
