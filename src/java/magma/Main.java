package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        try {
            Path source = Paths.get(".", "src", "java", "magma", "Main.java");
            String input = Files.readString(source);
            String output = compile(input);
            Files.writeString(source.resolveSibling("Main.c"), output);
        } catch (IOException | CompileException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }

    private static String compile(String input) throws CompileException {
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

        StringBuilder output = new StringBuilder();
        for (String segment : segments) {
            output.append(compileRootSegment(segment));
        }
        return output.toString();
    }

    private static String compileRootSegment(String input) throws CompileException {
        if (input.startsWith("package ")) return "";
        if (input.strip().startsWith("import ")) return "#include <temp.h>\n";

        int classIndex = input.indexOf("class ");
        if (classIndex >= 0) {
            String right = input.substring(classIndex + "class ".length());
            int contentStart = right.indexOf("{");
            if (contentStart >= 0) {
                String name = right.substring(0, contentStart).strip();
                String withEnd = right.substring(contentStart + "{".length()).strip();
                if (withEnd.endsWith("}")) {
                    String content = withEnd.substring(0, withEnd.length() - "}".length());
                    // TODO: parse content
                    return "struct " + name + " {\n};\n";
                }
            }
        }

        throw new CompileException("Invalid root segment", input);
    }
}
