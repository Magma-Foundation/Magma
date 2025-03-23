package magma;

import magma.result.Result;
import magma.result.Results;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static final Path SOURCE_DIRECTORY = Paths.get(".", "src", "java");

    public static void main(String[] args) {
        try (Stream<Path> paths = Files.walk(SOURCE_DIRECTORY)) {
            Set<Path> sources = paths.filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".java"))
                    .collect(Collectors.toSet());

            for (Path source : sources) {
                String input = Files.readString(source);
                String output = compile(input);

                Path relative = SOURCE_DIRECTORY.relativize(source);
                Path parent = relative.getParent();

                String nameWithExt = relative.getFileName().toString();
                String name = nameWithExt.substring(0, nameWithExt.lastIndexOf('.'));

                Path targetParent = Paths.get(".", "src", "windows").resolve(parent);
                if (!Files.exists(targetParent)) Files.createDirectories(targetParent);

                Path target = targetParent.resolve(name + ".c");
                Files.writeString(target, output);
            }
        } catch (CompileException | IOException e) {
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
            output.append(Results.unwrap(compileRootSegment(segment)));
        }

        return output.toString();
    }

    private static Result<String, CompileException> compileRootSegment(String segment) {
        return Results.wrap(() -> {
            if (segment.startsWith("package ")) return "";
            if (segment.strip().startsWith("import ")) return "#include <temp.h>\n";

            int classIndex = segment.indexOf("class ");
            if (classIndex >= 0) {
                String right = segment.substring(classIndex + "class ".length());
                int contentStart = right.indexOf("{");
                if (contentStart >= 0) {
                    String name = right.substring(0, contentStart).strip();
                    return "struct " + name + " {\n};\n";
                }
            }

            throw new CompileException("Invalid root segment", segment);
        });
    }

}
