package magma;

import jvm.api.collect.Lists;
import jvm.api.io.Paths;
import magma.api.io.Path_;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Application {
    public static final Path_ TARGET_DIRECTORY = Paths.get(".", "src", "clang");
    public static final Path_ SOURCE_DIRECTORY = Paths.get(".", "src", "java");

    public static Option<ApplicationError> runWithFiles(Set<Path_> files) {
        Set<Path_> sources = files.stream()
                .filter(Path_::isRegularFile)
                .filter(path -> path.asString().endsWith(".java"))
                .collect(Collectors.toSet());

        return runWithSources(sources).match(Application::build, Some::new);
    }

    private static Option<ApplicationError> build(List<Path_> relativePaths) {
        Path_ build = TARGET_DIRECTORY.resolve("build.bat");
        String joinedPaths = relativePaths.stream()
                .map(Path_::asString)
                .map(path -> ".\\" + path + "^\n\t")
                .collect(Collectors.joining(" "));

        String output = "clang " + joinedPaths + " -o main.exe";
        return build.writeString(output).map(ApplicationError::new).or(Application::build);
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

    private static Result<List<Path_>, ApplicationError> runWithSources(Set<Path_> sources) {
        Result<List<Path_>, ApplicationError> relativePaths = new Ok<>(new ArrayList<>());
        for (Path_ source : sources) {
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

    private static Result<Path_, ApplicationError> runWithSource(Source source, List_<String> namespace, String name) {
        return source.read()
                .mapErr(ApplicationError::new)
                .flatMapValue(input -> compileWithInput(namespace, name, input));
    }

    private static Result<Path_, ApplicationError> compileWithInput(List_<String> namespace, String name, String input) {
        State state = new ImmutableState(namespace, name);
        return Compiler.compile(state, input)
                .mapErr(ApplicationError::new)
                .flatMapValue(output -> writeOutput(namespace, name, output));
    }

    private static Result<Path_, ApplicationError> writeOutput(List_<String> namespace, String name, Tuple<String, String> output) {
        Path_ targetParent = namespace.stream().foldWithInitial(TARGET_DIRECTORY, Path_::resolve);

        return ensureDirectories(targetParent)
                .<Result<Path_, ApplicationError>>map(Err::new)
                .orElseGet(() -> getPathApplicationErrorResult(targetParent, name, output));
    }

    private static Result<Path_, ApplicationError> getPathApplicationErrorResult(Path_ parent, String name, Tuple<String, String> output) {
        Path_ header = parent.resolve(name + ".h");
        Path_ target = parent.resolve(name + ".c");
        return header.writeString(output.left())
                .or(() -> target.writeString(output.right()))
                .map(ApplicationError::new)
                .<Result<Path_, ApplicationError>>map(Err::new)
                .orElseGet(() -> new Ok<>(TARGET_DIRECTORY.relativize(target)));
    }

    private static Option<ApplicationError> ensureDirectories(Path_ targetParent) {
        if (targetParent.isExists()) return new None<>();
        return targetParent.createDirectoriesSafe().map(ApplicationError::new);
    }

    static Option<ApplicationError> run() {
        return SOURCE_DIRECTORY.walk()
                .mapErr(ApplicationError::new)
                .match(Application::runWithFiles, Some::new);
    }
}