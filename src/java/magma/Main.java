package magma;

import jvm.collect.list.Lists;
import jvm.io.Paths;
import jvm.process.Processes;
import magma.collect.stream.Joiner;
import magma.collect.list.List_;
import magma.collect.set.SetCollector;
import magma.collect.set.Set_;
import magma.compile.Compiler;
import magma.compile.source.PathSource;
import magma.compile.source.Source;
import magma.io.IOError;
import magma.io.Path_;
import magma.option.None;
import magma.option.Option;
import magma.option.Some;
import magma.result.Err;
import magma.result.Ok;
import magma.result.Result;

public class Main {
    public static final Path_ SOURCE_DIRECTORY = Paths.get(".", "src", "java");
    public static final Path_ TARGET_DIRECTORY = Paths.get(".", "src", "clang");

    public static void main(String[] args) {
        SOURCE_DIRECTORY.walk()
                .mapErr(ApplicationError::new)
                .match(Main::runWithFiles, Some::new)
                .ifPresent(error -> System.err.println(error.display()));
    }

    private static Option<ApplicationError> runWithFiles(Set_<Path_> files) {
        Set_<Path_> collect = files.stream()
                .filter(Path_::isRegularFile)
                .filter(path -> path.asString().endsWith(".java"))
                .collect(new SetCollector<>());

        return runWithSources(collect);
    }

    private static Option<ApplicationError> runWithSources(Set_<Path_> sources) {
        return sources.stream()
                .foldToResult(Lists.empty(), Main::foldIntoRelatives)
                .match(Main::complete, Some::new);
    }

    private static Option<ApplicationError> complete(List_<Path_> relatives) {
        Path_ build = TARGET_DIRECTORY.resolve("build.bat");
        String collect = relatives.stream()
                .map(Path_::asString)
                .map(path -> ".\\" + path + "^\n\t")
                .collect(new Joiner(""))
                .orElse("");

        return build.writeString("clang " + collect + " -o main.exe")
                .map(ApplicationError::new)
                .or(Main::startCommand);
    }

    private static Option<ApplicationError> startCommand() {
        return Processes.executeCommand(Lists.of("cmd.exe", "/c", "build.bat"), TARGET_DIRECTORY).map(ApplicationError::new);
    }

    private static Result<List_<Path_>, ApplicationError> foldIntoRelatives(List_<Path_> relatives, Path_ path) {
        return compileSource(new PathSource(SOURCE_DIRECTORY, path)).mapValue(maybeTarget -> maybeTarget.map(relatives::add).orElse(relatives));
    }

    private static Result<Option<Path_>, ApplicationError> compileSource(Source source) {
        List_<String> namespace = source.computeNamespace();
        if (isPlatformDependent(namespace)) return new Ok<>(new None<>());

        String name = source.computeName();
        return source.readString().mapErr(ApplicationError::new).flatMapValue(input -> {
            return Compiler.compile(input, namespace, name).mapErr(ApplicationError::new).flatMapValue(output -> {
                Path_ targetParent = namespace.stream().foldWithInitial(TARGET_DIRECTORY, Path_::resolve);
                return ensureDirectories(targetParent).map(ApplicationError::new).<Result<Option<Path_>, ApplicationError>>match(Err::new, () -> {
                    return writeOutput(targetParent, output, name);
                });
            });
        });
    }

    private static Result<Option<Path_>, ApplicationError> writeOutput(Path_ parent, String output, String name) {
        Path_ path1 = parent.resolve(name + ".c");
        Path_ path = parent.resolve(name + ".h");

        Option<IOError> option1 = path1.writeString(output);
        Option<IOError> option = path.writeString("");
        return option1.or(() -> option).map(ApplicationError::new).match(Err::new, () -> {
            Option<Path_> result = new Some<>(TARGET_DIRECTORY.relativize(parent.resolve(name + ".c")));
            return new Ok<>(result);
        });
    }

    private static Option<IOError> ensureDirectories(Path_ targetParent) {
        if (!targetParent.exists()) return targetParent.createAsDirectories();
        return new None<>();
    }

    private static boolean isPlatformDependent(List_<String> namespace) {
        return namespace.findFirst()
                .filter(first -> first.equals("jvm"))
                .isPresent();
    }
}
