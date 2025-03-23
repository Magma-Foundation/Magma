package magma;

import magma.java.result.JavaResults;
import magma.result.Result;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;
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
                Path relative = SOURCE_DIRECTORY.relativize(source);
                Path parent = relative.getParent();
                if (parent.startsWith(Paths.get("magma", "java"))) continue;

                String input = Files.readString(source);
                String output = compile(input);

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
            output.append(JavaResults.unwrap(compileRootSegment(segment)));
        }

        return output.toString();
    }

    private static Result<String, CompileException> compileRootSegment(String input) {
        return JavaResults.wrap(() -> {
            if (input.startsWith("package ")) return "";
            if (input.strip().startsWith("import ")) {
                String right = input.strip().substring("import ".length());
                if (right.endsWith(";")) {
                    String content = right.substring(0, right.length() - ";".length());
                    List<String> segments = Arrays.asList(content.split(Pattern.quote(".")));
                    if (segments.size() >= 3 && segments.subList(0, 3).equals(List.of("java", "util", "function")))
                        return "";

                    String joined = String.join("/", segments);
                    return "#include <" + joined + ".h>\n";
                }
            }

            Optional<String> maybeClass = compileClass(input);
            if (maybeClass.isPresent()) return maybeClass.get();

            if (input.contains("interface ") || input.contains("record ")) return generateStruct("Temp");

            throw new CompileException("Invalid root segment", input);
        });
    }

    private static Optional<String> compileClass(String input) {
        int classIndex = input.indexOf("class ");
        if (classIndex < 0) return Optional.empty();

        String right = input.substring(classIndex + "class ".length());
        int contentStart = right.indexOf("{");
        if (contentStart < 0) return Optional.empty();

        String beforeContent = right.substring(0, contentStart).strip();

        String name = computeName(beforeContent);
        return Optional.of(generateStruct(name));
    }

    private static String computeName(String beforeContent) {
        int implementsIndex = beforeContent.indexOf(" implements ");
        if (implementsIndex < 0) return beforeContent;

        String beforeImplements = beforeContent.substring(0, implementsIndex).strip();
        if (!beforeImplements.endsWith(">")) return beforeImplements;

        String withoutEnd = beforeImplements.substring(0, beforeImplements.length() - ">".length());
        int typeParamStart = withoutEnd.indexOf("<");
        if (typeParamStart < 0) return beforeImplements;

        return withoutEnd.substring(0, typeParamStart).strip();
    }

    private static String generateStruct(String name) {
        return "struct " + name + " {\n};\n";
    }

}
