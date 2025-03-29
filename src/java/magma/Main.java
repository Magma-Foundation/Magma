package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;
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
        } catch (CompileException | IOException | InterruptedException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }

    private static void runWithSources(Set<Path> sources) throws IOException, CompileException, InterruptedException {
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

        new ProcessBuilder("cmd.exe", "/c", "build.bat")
                .directory(TARGET_DIRECTORY.toFile())
                .inheritIO()
                .start()
                .waitFor();
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
        Optional<String> maybePackage = compilePackage(input);
        if (maybePackage.isPresent()) return input;

        Optional<String> maybeImport = compileImport(input);
        if (maybeImport.isPresent()) return maybeImport.get();

        Optional<String> maybeClass = compileClass(input);
        if (maybeClass.isPresent()) return maybeClass.get();

        Optional<String> maybeInterface = compileInterface(input);
        if (maybeInterface.isPresent()) return maybeInterface.get();

        throw new CompileException("Invalid root segment", input);
    }

    private static Optional<String> compilePackage(String input) {
        if (input.startsWith("package ")) return Optional.of("");
        return Optional.empty();
    }

    private static Optional<String> compileClass(String input) {
        if (input.contains("class ")) {
            return Optional.of("struct Temp {\n};\n");
        }
        return Optional.empty();
    }

    private static Optional<String> compileInterface(String input) {
        if (input.contains("interface ")) {
            return Optional.of("struct Temp {\n};\n");
        }
        return Optional.empty();
    }

    private static Optional<String> compileImport(String input) {
        if (!input.strip().startsWith("import ")) return Optional.empty();

        String right = input.strip().substring("import ".length());
        if (!right.endsWith(";")) return Optional.empty();

        String namespaceString = right.substring(0, right.length() - ";".length());
        String[] namespace = namespaceString.split(Pattern.quote("."));
        String joined = String.join("/", namespace);
        return Optional.of("#include \"" +
                joined +
                ".h\"\n");
    }
}
