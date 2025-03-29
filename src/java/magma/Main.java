package magma;

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
    public static final Path TARGET_DIRECTORY = Paths.get(".", "src", "clang");

    public static void main(String[] args) {
        try (Stream<Path> stream = Files.walk(SOURCE_DIRECTORY)) {
            Set<Path> sources = stream.filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".java"))
                    .collect(Collectors.toSet());

            runWithSources(sources);
        } catch (CompileException | IOException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }

    private static void runWithSources(Set<Path> sources) throws IOException, CompileException {
        for (Path source : sources) {
            runWithSource(source);
        }
    }

    private static void runWithSource(Path source) throws IOException, CompileException {
        String input = Files.readString(source);
        String output = compile(input);

        Path relative = SOURCE_DIRECTORY.relativize(source);
        Path parent = relative.getParent();

        String nameWithExt = relative.getFileName().toString();
        String name = nameWithExt.substring(0, nameWithExt.lastIndexOf("."));

        Path targetParent = TARGET_DIRECTORY.resolve(parent);
        if (!Files.exists(targetParent)) Files.createDirectories(targetParent);

        Path target = targetParent.resolve(name + ".c");
        Files.writeString(target, output);
    }

    private static String compile(String input) throws CompileException {
        ArrayList<String> segments = divide(input);

        StringBuilder builder = new StringBuilder();
        for (String segment : segments) {
            builder.append(compileRootSegment(segment));
        }

        return builder.toString();
    }

    private static ArrayList<String> divide(String input) {
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
        return segments;
    }

    private static String compileRootSegment(String input) throws CompileException {
        if (input.startsWith("package ")) return "";
        if (input.strip().startsWith("import ")) return "#include \"temp.h\"\n";
        if (input.contains("class ")) return "";
        throw new CompileException("Invalid root segment", input);
    }
}
