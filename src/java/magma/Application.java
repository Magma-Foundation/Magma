package magma;

import jv.api.JavaOptions;
import jv.api.collect.JavaLists;
import jv.api.error.ThrowableError;
import jv.api.io.JavaFiles;
import jv.api.result.JavaResults;
import jv.app.compile.PathSource;
import magma.api.result.Err;
import magma.api.result.Ok;
import magma.api.result.Result;
import magma.api.result.Tuple;
import magma.app.ApplicationError;
import magma.app.compile.ImmutableState;
import magma.app.compile.Source;
import magma.app.compile.State;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class Application {
    public static final Path TARGET_DIRECTORY = Paths.get(".", "src", "windows");
    public static final Path SOURCE_DIRECTORY = Paths.get(".", "src", "java");

    public static Optional<ApplicationError> runWithFiles(Set<Path> files) {
        Set<Path> sources = files.stream()
                .filter(Files::isRegularFile)
                .filter(path -> path.toString().endsWith(".java"))
                .collect(Collectors.toSet());

        return runWithSources(sources).match(Application::build, Optional::of);
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
                .or(Application::build);
    }

    private static Optional<ApplicationError> build() {
        ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", "build")
                .directory(TARGET_DIRECTORY.toFile())
                .inheritIO();

        return JavaResults.wrap(builder::start).mapErr(ThrowableError::new).mapErr(ApplicationError::new).match(process -> {
            Result<Integer, InterruptedException> awaited = JavaResults.wrap(process::waitFor);
            JavaOptions.unwrap(awaited.findValue()).ifPresent(exitCode -> {
                if (exitCode != 0) System.err.println("Invalid exit code: " + exitCode);
            });
            return JavaOptions.unwrap(awaited.findError()).map(ThrowableError::new).map(ApplicationError::new);
        }, Optional::of);
    }

    private static Result<List<Path>, ApplicationError> runWithSources(Set<Path> sources) {
        Result<List<Path>, ApplicationError> relativePaths = new Ok<List<Path>, ApplicationError>(new ArrayList<Path>());
        for (Path source : sources) {
            final Source wrapped = new PathSource(source);
            List<String> namespace = JavaLists.toNative(wrapped.computeNamespace());
            if (namespace.subList(0, 1).equals(List.of("jv"))) {
                continue;
            }

            relativePaths = relativePaths.and(() -> {
                return runWithSource(wrapped, namespace, wrapped.computeName());
            }).mapValue(tuple -> {
                tuple.left().add(tuple.right());
                return tuple.left();
            });
        }
        return relativePaths;
    }

    private static Result<Path, ApplicationError> runWithSource(Source source, List<String> namespace, String name) {
        return source.read()
                .mapErr(ApplicationError::new)
                .flatMapValue(input -> compileWithInput(input, namespace, name));
    }

    private static Result<Path, ApplicationError> compileWithInput(String input, List<String> namespace, String name) {
        State state = new ImmutableState(JavaLists.fromNative(namespace), name);
        return Compiler.compile(state, input)
                .mapErr(ApplicationError::new)
                .flatMapValue(output -> writeOutput(output, namespace, name));
    }

    private static Result<Path, ApplicationError> writeOutput(Tuple<String, String> output, List<String> namespace, String name) {
        Path targetParent = TARGET_DIRECTORY;
        for (String segment : namespace) {
            targetParent = targetParent.resolve(segment);
        }

        Optional<Result<Path, ApplicationError>> maybeError = ensureDirectories(targetParent).map(Err::new);
        if (maybeError.isPresent()) return maybeError.get();

        Path header = targetParent.resolve(name + ".h");
        Path target = targetParent.resolve(name + ".c");
        return JavaFiles.writeString(header, output.left())
                .or(() -> JavaFiles.writeString(target, output.right()))
                .map(ThrowableError::new)
                .map(ApplicationError::new)
                .<Result<Path, ApplicationError>>map(Err::new)
                .orElseGet(() -> new Ok<Path, ApplicationError>(TARGET_DIRECTORY.relativize(target)));
    }

    private static Optional<ApplicationError> ensureDirectories(Path targetParent) {
        if (Files.exists(targetParent)) return Optional.empty();

        return JavaFiles.createDirectoriesSafe(targetParent)
                .map(ThrowableError::new)
                .map(ApplicationError::new);
    }

    static Optional<ApplicationError> run() {
        return JavaFiles.walk(SOURCE_DIRECTORY)
                .mapErr(ThrowableError::new)
                .mapErr(ApplicationError::new)
                .match(Application::runWithFiles, Optional::of);
    }
}