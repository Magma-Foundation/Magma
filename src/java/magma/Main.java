package magma;

import magma.process.ExceptionalProcessError;
import magma.process.ProcessError;
import magma.process.Processes;
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
    public static final Path SOURCE_DIRECTORY = Paths.get(".", "src", "java");
    public static final Path TARGET_DIRECTORY = Paths.get(".", "src", "clang");

    public static void main(String[] args) {
        magma.io.Files.walk()
                .mapErr(ApplicationError::new)
                .match(Main::runWithFiles, Optional::of)
                .ifPresent(error -> System.err.println(error.display()));
    }

    private static Optional<ApplicationError> runWithFiles(Set<Path> files) {
        Set<Path> sources = files.stream()
                .filter(Files::isRegularFile)
                .filter(path -> path.toString().endsWith(".java"))
                .collect(Collectors.toSet());

        return runWithSources(sources).match(Main::writeBuildFile, Optional::of);
    }

    private static Optional<ApplicationError> writeBuildFile(List<Path> targetPaths) {
        Path build = TARGET_DIRECTORY.resolve("build.bat");
        String joined = targetPaths.stream()
                .map(Path::toString)
                .map(path -> path + "^\n\t")
                .collect(Collectors.joining());

        return magma.io.Files.writeString(build, "clang " + joined + "-o main.exe")
                .map(ApplicationError::new)
                .or(Main::startBuild);
    }

    private static Result<List<Path>, ApplicationError> runWithSources(Set<Path> sources) {
        Result<List<Path>, ApplicationError> targetPaths = new Ok<>(new ArrayList<>());
        for (Path source : sources) {
            targetPaths = targetPaths.and(() -> runWithSource(source)).mapValue(tuple -> {
                tuple.left().add(tuple.right());
                return tuple.left();
            });
        }
        return targetPaths;
    }

    private static Optional<ApplicationError> startBuild() {
        return Processes.startProcess(List.of("cmd", "/c", "build.bat"), TARGET_DIRECTORY)
                .mapErr(ApplicationError::new)
                .match(process -> waitFor(process).map(ApplicationError::new), Optional::of);
    }

    private static Optional<ProcessError> waitFor(Process process) {
        return Results.wrapRunnable(process::waitFor).map(ExceptionalProcessError::new);
    }

    private static Result<Path, ApplicationError> runWithSource(Path source) {
        return magma.io.Files.readString(source)
                .mapErr(ApplicationError::new)
                .flatMapValue(input -> getPathErrorResult(source, input));
    }

    private static Result<Path, ApplicationError> getPathErrorResult(Path source, String input) {
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
            output.append(compile(segment));
        }

        Path relative = SOURCE_DIRECTORY.relativize(source);
        Path parent = relative.getParent();

        Path targetParent = TARGET_DIRECTORY.resolve(parent);
        return ensureTargetParent(targetParent)
                .<Result<Path, ApplicationError>>map(Err::new)
                .orElseGet(() -> getResult(relative, targetParent, output));
    }

    private static Result<Path, ApplicationError> getResult(Path relative, Path targetParent, StringBuilder output) {
        String nameWithExt = relative.getFileName().toString();
        String name = nameWithExt.substring(0, nameWithExt.lastIndexOf("."));

        Path target = targetParent.resolve(name + ".c");
        return magma.io.Files.writeString(target, output.toString())
                .map(ApplicationError::new)
                .<Result<Path, ApplicationError>>map(Err::new)
                .orElseGet(() -> new Ok<>(TARGET_DIRECTORY.relativize(target)));
    }

    private static Optional<ApplicationError> ensureTargetParent(Path targetParent) {
        if (!Files.exists(targetParent)) {
            return magma.io.Files.createDirectories(targetParent).map(ApplicationError::new);
        }
        return Optional.empty();
    }

    private static Result<String, CompileError> compile(String segment) {
        if (segment.startsWith("package ")) return new Ok<>("");

        String stripped = segment.strip();
        if (stripped.startsWith("import ")) {
            String right = stripped.substring("import ".length());
            if (right.endsWith(";")) {
                String substring = right.substring(0, right.length() - ";".length());
                String replaced = String.join("/", substring.split(Pattern.quote(".")));
                return new Ok<>("#include \"" + replaced + ".h\"\n");
            }
        }

        if (segment.contains("class ")) return new Ok<>("struct Temp {\n};\n");
        return new Err<>(new CompileError("Invalid root segment", segment));
    }
}
