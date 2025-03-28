package magma;

import jvm.api.collect.Lists;
import jvm.api.io.JavaFiles;
import magma.api.process.Process_;
import jvm.api.process.Processes;
import jvm.app.compile.PathSource;
import magma.api.collect.List_;
import magma.api.option.None;
import magma.api.option.Option;
import magma.api.option.Some;
import magma.api.result.Err;
import magma.api.result.Ok;
import magma.api.result.Result;
import magma.api.result.Tuple;
import magma.app.ApplicationError;
import magma.app.compile.ImmutableState;
import magma.api.concurrent.InterruptedError;
import magma.app.compile.Source;
import magma.app.compile.State;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Application {
    public static final Path TARGET_DIRECTORY = Paths.get(".", "src", "clang");
    public static final Path SOURCE_DIRECTORY = Paths.get(".", "src", "java");

    public static Option<ApplicationError> runWithFiles(Set<Path> files) {
        Set<Path> sources = files.stream()
                .filter(Files::isRegularFile)
                .filter(path -> path.toString().endsWith(".java"))
                .collect(Collectors.toSet());

        return runWithSources(sources).match(Application::build, Some::new);
    }

    private static Option<ApplicationError> build(List<Path> relativePaths) {
        Path build = TARGET_DIRECTORY.resolve("build.bat");
        String joinedPaths = relativePaths.stream()
                .map(Path::toString)
                .map(path -> ".\\" + path + "^\n\t")
                .collect(Collectors.joining(" "));

        String output = "clang " + joinedPaths + " -o main.exe";
        return JavaFiles.writeString(build, output).map(ApplicationError::new).or(Application::build);
    }

    private static Option<ApplicationError> build() {
        return Processes.start(Lists.of("cmd.exe", "/c", "build"), TARGET_DIRECTORY)
                .mapErr(ApplicationError::new)
                .match(Application::awaitProcess, Some::new);
    }

    private static Option<ApplicationError> awaitProcess(Process_ process_) {
        Result<Integer, InterruptedError> awaited = process_.waitFor();

        awaited.findValue().ifPresent(exitCode -> {
            if (exitCode != 0) System.err.println("Invalid exit code: " + exitCode);
        });

        return awaited.findError().map(ApplicationError::new);
    }

    private static Result<List<Path>, ApplicationError> runWithSources(Set<Path> sources) {
        Result<List<Path>, ApplicationError> relativePaths = new Ok<List<Path>, ApplicationError>(new ArrayList<Path>());
        for (Path source : sources) {
            final Source wrapped = new PathSource(source);
            List_<String> namespace = wrapped.computeNamespace();
            List_<String> slice = namespace.subList(0, 1).orElse(Lists.empty());
            if (Lists.equalsTo(slice, Lists.of("jvm"), String::equals)) {
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

    private static Result<Path, ApplicationError> runWithSource(Source source, List_<String> namespace, String name) {
        return source.read()
                .mapErr(ApplicationError::new)
                .flatMapValue(input -> compileWithInput(namespace, name, input));
    }

    private static Result<Path, ApplicationError> compileWithInput(List_<String> namespace, String name, String input) {
        State state = new ImmutableState(namespace, name);
        return Compiler.compile(state, input)
                .mapErr(ApplicationError::new)
                .flatMapValue(output -> writeOutput(namespace, name, output));
    }

    private static Result<Path, ApplicationError> writeOutput(List_<String> namespace, String name, Tuple<String, String> output) {
        Path targetParent = namespace.stream().fold(TARGET_DIRECTORY, Path::resolve);

        return ensureDirectories(targetParent)
                .<Result<Path, ApplicationError>>map(Err::new)
                .orElseGet(() -> getPathApplicationErrorResult(targetParent, name, output));
    }

    private static Result<Path, ApplicationError> getPathApplicationErrorResult(Path parent, String name, Tuple<String, String> output) {
        Path header = parent.resolve(name + ".h");
        Path target = parent.resolve(name + ".c");
        return JavaFiles.writeString(header, output.left())
                .or(() -> JavaFiles.writeString(target, output.right()))
                .map(ApplicationError::new)
                .<Result<Path, ApplicationError>>map(Err::new)
                .orElseGet(() -> new Ok<>(TARGET_DIRECTORY.relativize(target)));
    }

    private static Option<ApplicationError> ensureDirectories(Path targetParent) {
        if (Files.exists(targetParent)) return new None<>();
        return JavaFiles.createDirectoriesSafe(targetParent).map(ApplicationError::new);
    }

    static Option<ApplicationError> run() {
        return JavaFiles.walk(SOURCE_DIRECTORY)
                .mapErr(ApplicationError::new)
                .match(Application::runWithFiles, Some::new);
    }
}