package magma;

import magma.java.JavaFiles;
import magma.result.Results;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static final Path TARGET_DIRECTORY = Paths.get(".", "src", "windows");
    public static final Path SOURCE_DIRECTORY = Paths.get(".", "src", "java");

    public static void main(String[] args) {
        try {
            try (Stream<Path> stream = Results.unwrap(JavaFiles.walk(SOURCE_DIRECTORY)).stream()) {
                Set<Path> sources = stream.filter(Files::isRegularFile)
                        .filter(path -> path.toString().endsWith(".java"))
                        .collect(Collectors.toSet());

                List<Path> relativePaths = getPaths(sources);

                Path build = TARGET_DIRECTORY.resolve("build.bat");
                String joinedPaths = relativePaths.stream()
                        .map(Path::toString)
                        .map(path -> ".\\" + path + "^\n\t")
                        .collect(Collectors.joining(" "));

                String output = "clang " + joinedPaths + " -o main.exe";
                JavaFiles.writeString(build, output);

                Process process = new ProcessBuilder("cmd.exe", "/c", "build")
                        .directory(TARGET_DIRECTORY.toFile())
                        .inheritIO()
                        .start();

                process.waitFor();
            }
        } catch (CompileException | InterruptedException | IOException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }

    private static List<Path> getPaths(Set<Path> sources) throws IOException, CompileException {
        List<Path> relativePaths = new ArrayList<>();
        for (Path source : sources) {
            relativePaths.add(getPath(source));
        }
        return relativePaths;
    }

    private static Path getPath(Path source) throws IOException, CompileException {
        String input = Results.unwrap(JavaFiles.readSafe(source));

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

        Path relative = SOURCE_DIRECTORY.relativize(source);
        Path parent = relative.getParent();
        Path targetParent = TARGET_DIRECTORY.resolve(parent);
        if (!Files.exists(targetParent)) Files.createDirectories(targetParent);

        String nameWithExt = relative.getFileName().toString();
        String name = nameWithExt.substring(0, nameWithExt.lastIndexOf("."));
        Path target = targetParent.resolve(name + ".c");

        JavaFiles.writeString(target, output.toString());
        Path relative1 = TARGET_DIRECTORY.relativize(target);
        return relative1;
    }

    private static String compileRootSegment(String segment) throws CompileException {
        if (segment.startsWith("package ")) return "";

        if (segment.strip().startsWith("import ")) {
            String right = segment.strip().substring("import ".length());
            if (right.endsWith(";")) {
                String left = right.substring(0, right.length() - ";".length());
                String[] namespace = left.split(Pattern.quote("."));
                String joined = String.join("/", namespace);
                return "#include <" +
                        joined +
                        ".h>\n";
            }
        }

        if (segment.contains("class ")) return "struct Temp {\n};\n";
        throw new CompileException("Invalid root segment", segment);
    }
}
