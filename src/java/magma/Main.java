package magma;

import jvm.collect.list.Lists;
import jvm.io.Paths;
import magma.collect.Joiner;
import magma.collect.set.SetCollector;
import magma.collect.set.Set_;
import magma.collect.list.List_;
import magma.compile.CompileException;
import magma.compile.Compiler;
import magma.compile.PathSource;
import magma.compile.Source;
import magma.io.Path_;
import magma.option.None;
import magma.option.Option;
import magma.option.Some;
import magma.result.Err;
import magma.result.Ok;
import magma.result.Result;
import magma.result.Results;

import java.io.IOException;

public class Main {
    public static final Path_ SOURCE_DIRECTORY = Paths.get(".", "src", "java");
    public static final Path_ TARGET_DIRECTORY = Paths.get(".", "src", "clang");

    public static void main(String[] args) {
        try {
            Set_<Path_> filter = SOURCE_DIRECTORY.walk()
                    .stream()
                    .filter(Path_::isRegularFile)
                    .collect(new SetCollector<>());

            Set_<Path_> sources = filter
                    .stream()
                    .filter(path -> path.asString().endsWith(".java"))
                    .collect(new SetCollector<>());

            runWithSources(sources);
        } catch (IOException | ApplicationException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }

    private static void runWithSources(Set_<Path_> sources) throws ApplicationException {
        List_<Path_> relatives = Results.unwrap(sources.stream().foldToResult(Lists.empty(), Main::foldIntoRelatives));

        Path_ build = TARGET_DIRECTORY.resolve("build.bat");
        String collect = relatives.stream()
                .map(Path_::asString)
                .map(path -> ".\\" + path + "^\n\t")
                .collect(new Joiner(""))
                .orElse("");

        try {
            build.writeString("clang " + collect + " -o main.exe");
        } catch (IOException e) {
            throw new ApplicationException(e);
        }

        try {
            new ProcessBuilder("cmd.exe", "/c", "build.bat")
                    .directory(Paths.toNative(TARGET_DIRECTORY).toFile())
                    .inheritIO()
                    .start()
                    .waitFor();
        } catch (InterruptedException | IOException e) {
            throw new ApplicationException(e);
        }
    }

    private static Result<List_<Path_>, ApplicationException> foldIntoRelatives(List_<Path_> relatives, Path_ path) {
        return getPathOption(new PathSource(SOURCE_DIRECTORY, path)).mapValue(maybeTarget -> maybeTarget.map(relatives::add).orElse(relatives));
    }

    private static Result<Option<Path_>, ApplicationException> getPathOption(Source source) {
        List_<String> namespace = source.computeNamespace();
        if (isPlatformDependent(namespace)) return new Ok<>(new None<>());

        try {
            String name = source.computeName();
            String input = source.readString();
            String output = Compiler.compile(input, namespace, name);
            Path_ targetParent = namespace.stream().foldWithInitial(TARGET_DIRECTORY, Path_::resolve);
            if (!targetParent.exists()) {
                targetParent.createAsDirectories();
            }
            targetParent.resolve(name + ".c").writeString(output);
            targetParent.resolve(name + ".h").writeString("");
            Option<Path_> result = new Some<>(TARGET_DIRECTORY.relativize(targetParent.resolve(name + ".c")));

            return new Ok<>(result);
        } catch (IOException | CompileException e) {
            return new Err<>(new ApplicationException(e));
        }
    }

    private static boolean isPlatformDependent(List_<String> namespace) {
        return namespace.findFirst()
                .filter(first -> first.equals("jvm"))
                .isPresent();
    }
}
