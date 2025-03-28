package magma;

import magma.error.ApplicationError;
import magma.error.CompileError;
import magma.error.ThrowableError;
import magma.java.JavaFiles;
import magma.java.Options;
import magma.result.Err;
import magma.result.Ok;
import magma.result.Result;
import magma.result.Results;
import magma.result.Tuple;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {
    public static final Path TARGET_DIRECTORY = Paths.get(".", "src", "windows");

    public static void main(String[] args) {
        JavaFiles.walk(Source.SOURCE_DIRECTORY)
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
            Options.unwrap(awaited.findValue()).ifPresent(exitCode -> {
                if (exitCode != 0) System.err.println("Invalid exit code: " + exitCode);
            });
            return Options.unwrap(awaited.findError()).map(ThrowableError::new).map(ApplicationError::new);
        }, Optional::of);
    }

    private static Result<List<Path>, ApplicationError> runWithSources(Set<Path> sources) {
        Result<List<Path>, ApplicationError> relativePaths = new Ok<>(new ArrayList<>());
        for (Path source : sources) {
            final Source wrapped = new Source(source);
            List<String> namespace = wrapped.computeNamespace();
            if (namespace.equals(List.of("magma", "java"))) {
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
                .mapErr(ThrowableError::new)
                .mapErr(ApplicationError::new)
                .flatMapValue(input -> compileWithInput(input, namespace, name));
    }

    private static Result<Path, ApplicationError> compileWithInput(String input, List<String> namespace, String name) {
        State state = new State(namespace, name);
        return compile(state, input)
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
                .orElseGet(() -> new Ok<>(TARGET_DIRECTORY.relativize(target)));
    }

    private static Result<Tuple<String, String>, CompileError> compile(State state, String input) {
        return divideAndCompile(input, state).mapValue(tuple -> {
            String newSource = attachSource(state, tuple);
            return new Tuple<>(tuple.left(), newSource);
        });
    }

    private static String attachSource(State state, Tuple<String, String> tuple) {
        String name = state.name();
        String source = generateImport(name) + tuple.right();
        if (state.namespace().equals(List.of("magma")) && name.equals("Main")) {
            return source + "int main(){\n\treturn 0;\n}\n";
        } else {
            return source;
        }
    }

    private static String generateImport(String content) {
        return "#include \"" + content + ".h" + "\"\n";
    }

    private static Result<Tuple<String, String>, CompileError> divideAndCompile(String input, State state) {
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

        Result<Tuple<StringBuilder, StringBuilder>, CompileError> output = new Ok<>(new Tuple<>(new StringBuilder(), new StringBuilder()));
        for (String segment : segments) {
            output = output
                    .and(() -> compileRootSegment(segment, state))
                    .mapValue(Main::appendBuilders);
        }

        return output.mapValue(tuple -> new Tuple<>(tuple.left().toString(), tuple.right().toString()));
    }

    private static Tuple<StringBuilder, StringBuilder> appendBuilders(Tuple<Tuple<StringBuilder, StringBuilder>, Tuple<String, String>> tuple) {
        Tuple<StringBuilder, StringBuilder> builders = tuple.left();
        Tuple<String, String> elements = tuple.right();
        StringBuilder newLeft = builders.left().append(elements.left());
        StringBuilder newRight = builders.right().append(elements.right());
        return new Tuple<>(newLeft, newRight);
    }

    private static Optional<ApplicationError> ensureDirectories(Path targetParent) {
        if (Files.exists(targetParent)) return Optional.empty();

        return JavaFiles.createDirectoriesSafe(targetParent)
                .map(ThrowableError::new)
                .map(ApplicationError::new);
    }

    private static Result<Tuple<String, String>, CompileError> compileRootSegment(String segment, State state) {
        if (segment.startsWith("package ")) return new Ok<>(new Tuple<>("", ""));

        if (segment.strip().startsWith("import ")) {
            String right = segment.strip().substring("import ".length());
            if (right.endsWith(";")) {
                String left = right.substring(0, right.length() - ";".length());
                List<String> namespace = new ArrayList<>(Arrays.asList(left.split(Pattern.quote("."))));
                if (namespace.size() >= 3 && namespace.subList(0, 3).equals(List.of("java", "util", "function"))) {
                    return new Ok<>(new Tuple<>("", ""));
                }

                List<String> copy = new ArrayList<>();

                List<String> namespace1 = state.namespace();
                for (int i = 0; i < namespace1.size(); i++) {
                    copy.add("..");
                }

                if (namespace.isEmpty()) copy.add(".");
                copy.addAll(namespace);

                String joined = String.join("/", copy);
                return new Ok<>(new Tuple<>(generateImport(joined), ""));
            }
        }

        if (segment.contains("class ") || segment.contains("record ") || segment.contains("interface "))
            return new Ok<>(new Tuple<>("struct Temp {\n};\n", ""));

        return new Err<>(new CompileError("Invalid root segment", segment));
    }
}
