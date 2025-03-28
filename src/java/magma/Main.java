package magma;

import magma.error.ApplicationError;
import magma.error.CompileError;
import magma.error.ThrowableError;
import magma.java.JavaFiles;
import magma.result.Err;
import magma.result.Ok;
import magma.result.Result;
import magma.result.Results;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {
    public static final Path TARGET_DIRECTORY = Paths.get(".", "src", "windows");
    public static final Path SOURCE_DIRECTORY = Paths.get(".", "src", "java");

    public static void main(String[] args) {
        JavaFiles.walk(SOURCE_DIRECTORY)
                .mapErr(ThrowableError::new)
                .mapErr(ApplicationError::new)
                .match(Main::runWithFiles, Optional::of)
                .ifPresent(error -> System.err.println(error.display()));
    }

    private static Optional<ApplicationError> runWithFiles(Set<Path> files) {
        Set<Path> sources = files.stream()
                .filter(Files::isRegularFile)
                .filter(path -> path.toString().endsWith(".java"))
                .collect(Collectors.toSet());

        return runWithSources(sources).match(Main::build, Optional::of);
    }

    private static Optional<ApplicationError> build(List<Path> relativePaths) {
        Path build = TARGET_DIRECTORY.resolve("build.bat");
        String joinedPaths = relativePaths.stream()
                .map(Path::toString)
                .map(path -> ".\\" + path + "^\n\t")
                .collect(Collectors.joining(" "));

        String output = "clang " + joinedPaths + " -o main.exe";
        return JavaFiles.writeString(build, output)
                .map(ThrowableError::new)
                .map(ApplicationError::new)
                .or(Main::build);
    }

    private static Optional<ApplicationError> build() {
        ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", "build")
                .directory(TARGET_DIRECTORY.toFile())
                .inheritIO();

        return Results.wrap(builder::start).mapErr(ThrowableError::new).mapErr(ApplicationError::new).match(process -> {
            Result<Integer, InterruptedException> awaited = Results.wrap(process::waitFor);
            awaited.findValue().ifPresent(exitCode -> {
                if (exitCode != 0) System.err.println("Invalid exit code: " + exitCode);
            });
            return awaited.findError().map(ThrowableError::new).map(ApplicationError::new);
        }, Optional::of);
    }

    private static Result<List<Path>, ApplicationError> runWithSources(Set<Path> sources) {
        Result<List<Path>, ApplicationError> relativePaths = new Ok<>(new ArrayList<>());
        for (Path source : sources) {
            relativePaths = relativePaths.and(() -> runWithSource(source)).mapValue(tuple -> {
                tuple.left().add(tuple.right());
                return tuple.left();
            });
        }
        return relativePaths;
    }

    private static Result<Path, ApplicationError> runWithSource(Path source) {
        return JavaFiles.readSafe(source)
                .mapErr(ThrowableError::new)
                .mapErr(ApplicationError::new)
                .flatMapValue(input -> compileWithInput(source, input));
    }

    private static Result<Path, ApplicationError> compileWithInput(Path source, String input) {
        return compile(input)
                .mapErr(ApplicationError::new)
                .flatMapValue(output -> writeOutput(source, output));
    }

    private static Result<Path, ApplicationError> writeOutput(Path source, String output) {
        Path relative = SOURCE_DIRECTORY.relativize(source);
        Path parent = relative.getParent();
        Path targetParent = TARGET_DIRECTORY.resolve(parent);
        Optional<Result<Path, ApplicationError>> maybeError = ensureDirectories(targetParent).map(Err::new);
        if (maybeError.isPresent()) return maybeError.get();

        String nameWithExt = relative.getFileName().toString();
        String name = nameWithExt.substring(0, nameWithExt.lastIndexOf("."));
        Path target = targetParent.resolve(name + ".c");

        return JavaFiles.writeString(target, output)
                .map(ThrowableError::new)
                .map(ApplicationError::new)
                .<Result<Path, ApplicationError>>map(Err::new)
                .orElseGet(() -> new Ok<>(TARGET_DIRECTORY.relativize(target)));
    }

    private static Result<String, CompileError> compile(String input) {
        List<String> segments = new ArrayList<>();
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

        Result<StringBuilder, CompileError> output = new Ok<>(new StringBuilder());
        for (String segment : segments) {
            output = output
                    .and(() -> compileRootSegment(segment))
                    .mapValue(tuple -> tuple.left().append(tuple.right()));
        }

        return output.mapValue(StringBuilder::toString);
    }

    private static Optional<ApplicationError> ensureDirectories(Path targetParent) {
        if (Files.exists(targetParent)) return Optional.empty();

        return JavaFiles.createDirectoriesSafe(targetParent)
                .map(ThrowableError::new)
                .map(ApplicationError::new);
    }

    private static Result<String, CompileError> compileRootSegment(String segment) {
        if (segment.startsWith("package ")) return new Ok<>("");

        if (segment.strip().startsWith("import ")) {
            String right = segment.strip().substring("import ".length());
            if (right.endsWith(";")) {
                String left = right.substring(0, right.length() - ";".length());
                String[] namespace = left.split(Pattern.quote("."));
                String joined = String.join("/", namespace);
                return new Ok<>("#include <" +
                        joined +
                        ".h>\n");
            }
        }

        if (segment.contains("class ") || segment.contains("record ") || segment.contains("interface ")) return new Ok<>("struct Temp {\n};\n");
        return new Err<>(new CompileError("Invalid root segment", segment));
    }
}
