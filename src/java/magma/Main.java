package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.function.Function;

public class Main {
    public static void main(String[] args) {
        try {
            Path source = Paths.get(".", "src", "java", "magma", "Main.java");
            String input = Files.readString(source);
            String output = Results.unwrap(compile(input));
            Files.writeString(source.resolveSibling("Main.c"), output);
        } catch (IOException | CompileException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }

    private static Result<String, CompileException> compile(String input) {
        return compileAll(input, Main::compileRootSegment);
    }

    private static Result<String, CompileException> compileAll(String input, Function<String, Result<String, CompileException>> compiler) {
        ArrayList<String> segments = new ArrayList<>();
        StringBuilder buffer = new StringBuilder();
        int depth = 0;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            buffer.append(c);
            if (c == ';' && depth == 0) {
                segments.add(buffer.toString());
                buffer = new StringBuilder();
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

    private static Result<String, CompileException> compileRootSegment(String segment) {
        if (segment.startsWith("package ")) return new Ok<>("");
        if (segment.strip().startsWith("import ")) return new Ok<>("#include <temp.h>\n");

        int classIndex = segment.indexOf("class ");
        if (classIndex >= 0) {
            String right = segment.substring(classIndex + "class ".length());
            int contentStart = right.indexOf("{");
            if (contentStart >= 0) {
                String name = right.substring(0, contentStart).strip();
                String withEnd = right.substring(contentStart + "{".length()).strip();
                if (withEnd.endsWith("}")) {
                    String inputContent = withEnd.substring(0, withEnd.length() - "}".length());
                    return compileAll(inputContent, Main::compileClassSegment)
                            .mapValue(outputContent -> "struct " + name + " {\n};\n" + outputContent);
                }
            }
        }

        return getRootSegment("root segment", segment);
    }

    private static Err<String, CompileException> getRootSegment(String type, String segment) {
        return new Err<>(new CompileException("Invalid " + type, segment));
    }

    private static Result<String, CompileException> compileClassSegment(String input) {
        return getRootSegment("class segment", input);
    }
}
