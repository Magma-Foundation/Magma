package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
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
        ArrayList<Path> relatives = new ArrayList<>();
        for (Path source : sources) {
            relatives.add(runWithSource(source));
        }

        Path build = TARGET_DIRECTORY.resolve("build.bat");
        String collect = relatives.stream()
                .map(Path::toString)
                .map(path -> ".\\" + path + "^\n\t")
                .collect(Collectors.joining());

        Files.writeString(build, "clang " +
                collect +
                "-o main.exe");
    }

    private static Path runWithSource(Path source) throws IOException, CompileException {
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

        return TARGET_DIRECTORY.relativize(target);
    }

    private static String compile(String input) throws CompileException {
        List<String> segments = divideStatements(input);

        StringBuilder builder = new StringBuilder();
        for (String segment : segments) {
            builder.append(compileRootSegment(segment));
        }

        return builder.toString();
    }

    private static List<String> divideStatements(String input) {
        DividingState current = new MutableDividingState();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            current = divideStatementChar(current, c);
        }

        return current.advance().segments();
    }

    private static DividingState divideStatementChar(DividingState current, char c) {
        DividingState appended = current.append(c);
        if (c == ';' && appended.isLevel()) return appended.advance();
        if (c == '{') appended.enter();
        if (c == '}') appended.exit();
        return appended;
    }

    private static String compileRootSegment(String input) throws CompileException {
        if (input.startsWith("package ")) return "";
        if (input.strip().startsWith("import ")) return "#include \"temp.h\"\n";
        if (input.contains("class ") || input.contains("interface ")) return "struct Temp {\n};\n";
        throw new CompileException("Invalid root segment", input);
    }
}
