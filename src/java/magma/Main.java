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
            Set<Path> sources = stream
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".java"))
                    .collect(Collectors.toSet());

            ArrayList<Path> targetPaths = new ArrayList<>();
            for (Path source : sources) {
                String input = Files.readString(source);

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
                    output.append(extracted(segment));
                }

                Path relative = SOURCE_DIRECTORY.relativize(source);
                Path parent = relative.getParent();

                Path targetParent = TARGET_DIRECTORY.resolve(parent);
                if (!Files.exists(targetParent)) {
                    Files.createDirectories(targetParent);
                }

                String nameWithExt = relative.getFileName().toString();
                String name = nameWithExt.substring(0, nameWithExt.lastIndexOf("."));

                Path target = targetParent.resolve(name + ".c");
                Files.writeString(target, output);

                targetPaths.add(TARGET_DIRECTORY.relativize(target));
            }

            Path build = TARGET_DIRECTORY.resolve("build.bat");
            String joined = targetPaths.stream()
                    .map(Path::toString)
                    .map(path -> path + "^\n\t")
                    .collect(Collectors.joining());

            Files.writeString(build, "clang " + joined + "-o main.exe");

            new ProcessBuilder("cmd", "/c", "build.bat")
                    .directory(TARGET_DIRECTORY.toFile())
                    .inheritIO()
                    .start()
                    .waitFor();
        } catch (IOException | CompileException | InterruptedException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }

    private static String extracted(String segment) throws CompileException {
        if (segment.startsWith("package ")) return "";
        if (segment.strip().startsWith("import ")) return "#include <temp.h>\n";
        if (segment.contains("class ")) return "struct Temp {\n};\n";
        throw new CompileException("Invalid root segment", segment);
    }
}
