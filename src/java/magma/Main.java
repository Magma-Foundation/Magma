package magma;

import jvm.collect.list.Lists;
import magma.collect.list.List_;
import magma.compile.CompileException;
import magma.compile.Compiler;
import magma.option.None;
import magma.option.Option;
import magma.option.Some;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
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
        } catch (CompileException | IOException | InterruptedException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }

    private static void runWithSources(Set<Path> sources) throws IOException, CompileException, InterruptedException {
        ArrayList<Path> relatives = new ArrayList<>();
        for (Path source : sources) {
            runWithSource(source).ifPresent(relatives::add);
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

    private static Option<Path> runWithSource(Path source) throws IOException, CompileException {
        Path relative = SOURCE_DIRECTORY.relativize(source);
        Path parent = relative.getParent();

        List_<String> namespace = Lists.empty();
        for (int i = 0; i < parent.getNameCount(); i++) {
            namespace = namespace.add(parent.getName(i).toString());
        }

        if (namespace.findFirst()
                .filter(first -> first.equals("jvm"))
                .isPresent()) {
            return new None<>();
        }

        String nameWithExt = relative.getFileName().toString();
        String name = nameWithExt.substring(0, nameWithExt.lastIndexOf("."));

        String input = Files.readString(source);
        String output = Compiler.compile(input, namespace, name);

        Path targetParent = TARGET_DIRECTORY.resolve(parent);
        if (!Files.exists(targetParent)) Files.createDirectories(targetParent);

        Path target = targetParent.resolve(name + ".c");
        Files.writeString(target, output);

        Path header = targetParent.resolve(name + ".h");
        Files.writeString(header, "");

        return new Some<>(TARGET_DIRECTORY.relativize(target));
    }
}
