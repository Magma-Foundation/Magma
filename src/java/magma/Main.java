package magma;

import jvm.collect.list.Lists;
import jvm.io.Paths;
import jvm.process.Processes;
import magma.collect.list.List_;
import magma.collect.map.Map_;
import magma.collect.set.SetCollector;
import magma.collect.set.Set_;
import magma.collect.stream.Joiner;
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
        return compileSource(new PathSource(SOURCE_DIRECTORY, path)).mapValue(relatives::addAll);
    }

    private static Result<List_<Path_>, ApplicationError> compileSource(Source source) {
        List_<String> namespace = source.computeNamespace();
        if (isPlatformDependent(namespace)) return new Ok<>(Lists.empty());

        String name = source.computeName();
        return source.readString()
                .mapErr(ApplicationError::new)
                .flatMapValue(input -> compileAndWrite(input, namespace, name));
    }

    private static Result<List_<Path_>, ApplicationError> compileAndWrite(
            String input,
            List_<String> namespace,
            String name
    ) {
        return Compiler.compile(input, namespace, name)
                .mapErr(ApplicationError::new)
                .flatMapValue(output -> writeOutputs(output, namespace, name));
    }

    private static Result<List_<Path_>, ApplicationError> writeOutputs(
            Map_<String, String> output,
            List_<String> namespace,
            String name
    ) {
        Path_ targetParent = namespace.stream().foldWithInitial(TARGET_DIRECTORY, Path_::resolve);
        return ensureDirectories(targetParent)
                .map(ApplicationError::new)
                .match(Err::new, () -> output.stream().foldToResult(Lists.empty(), (pathList, tuple) -> writeAndFoldOutput(pathList, targetParent, name, tuple.left(), tuple.right())));
    }

    private static Result<List_<Path_>, ApplicationError> writeAndFoldOutput(
            List_<Path_> current,
            Path_ targetParent,
            String name,
            String extension,
            String output
    ) {
        return writeOutput(targetParent, name, extension, output).mapValue(current::add);
    }

    private static Result<Path_, ApplicationError> writeOutput(Path_ parent, String name, String extension, String output) {
        return parent.resolve(name + extension)
                .writeString(output)
                .map(ApplicationError::new)
                .<Result<Path_, ApplicationError>>match(Err::new,
                        () -> new Ok<>(TARGET_DIRECTORY.relativize(parent.resolve(name + ".c"))));
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
