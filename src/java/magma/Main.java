package magma;

import jvm.result.Results;
import magma.compile.CompileError;
import magma.compile.rule.InfixRule;
import magma.compile.MapNode;
import magma.compile.rule.OrRule;
import magma.compile.rule.PrefixRule;
import magma.compile.rule.Rule;
import magma.compile.context.StringContext;
import magma.compile.rule.StringRule;
import magma.compile.rule.SuffixRule;
import magma.process.ExceptionalProcessError;
import magma.process.ProcessError;
import magma.process.Processes;
import magma.result.Err;
import magma.result.Ok;
import magma.result.Result;

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
    public static final Path SOURCE_DIRECTORY = Paths.get(".", "src", "java");
    public static final Path TARGET_DIRECTORY = Paths.get(".", "src", "clang");
    public static final List<String> FUNCTIONAL_PACKAGE = List.of("java", "util", "function");

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
            List<String> namespace = computeNamespace(source);
            if (namespace.isEmpty() || namespace.getFirst().equals("jvm")) continue;

            targetPaths = targetPaths.and(() -> runWithSource(source, namespace)).mapValue(tuple -> {
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

    private static Result<Path, ApplicationError> runWithSource(Path source, List<String> namespace) {
        return magma.io.Files.readString(source)
                .mapErr(ApplicationError::new)
                .flatMapValue(input -> compile(source, namespace, input));
    }

    private static Result<Path, ApplicationError> compile(Path source, List<String> namespace, String input) {
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

        Result<StringBuilder, CompileError> builder = new Ok<>(new StringBuilder());
        for (String segment : segments) {
            builder = builder.and(() -> compileRootSegment(segment, namespace)).mapValue(tuple -> {
                tuple.left().append(tuple.right());
                return tuple.left();
            });
        }

        return builder.mapErr(ApplicationError::new).flatMapValue(output -> {
            Path targetParent = TARGET_DIRECTORY;
            for (String s : namespace) {
                targetParent = targetParent.resolve(s);
            }

            Path finalTargetParent = targetParent;
            return ensureTargetParent(targetParent)
                    .<Result<Path, ApplicationError>>map(Err::new)
                    .orElseGet(() -> writeOutput(source, finalTargetParent, output));
        });
    }

    private static List<String> computeNamespace(Path source) {
        List<String> namespace = new ArrayList<>();
        Path relative = SOURCE_DIRECTORY.relativize(source);
        Path parent = relative.getParent();
        for (int i = 0; i < parent.getNameCount(); i++) {
            namespace.add(parent.getName(i).toString());
        }
        return namespace;
    }

    private static Result<Path, ApplicationError> writeOutput(Path relative, Path targetParent, StringBuilder output) {
        String nameWithExt = relative.getFileName().toString();
        String name = nameWithExt.substring(0, nameWithExt.lastIndexOf("."));

        return writeSafe(targetParent, name, ".c", output.toString())
                .or(() -> writeSafe(targetParent, name, ".h", output.toString()))
                .<Result<Path, ApplicationError>>map(Err::new)
                .orElseGet(() -> new Ok<>(TARGET_DIRECTORY.relativize(targetParent.resolve(name + ".c"))));
    }

    private static Optional<ApplicationError> writeSafe(
            Path targetParent,
            String name,
            String header,
            String output
    ) {
        Path resolve = targetParent.resolve(name + header);
        return magma.io.Files.writeString(resolve, output).map(ApplicationError::new);
    }

    private static Optional<ApplicationError> ensureTargetParent(Path targetParent) {
        if (!Files.exists(targetParent)) {
            return magma.io.Files.createDirectories(targetParent).map(ApplicationError::new);
        }
        return Optional.empty();
    }

    private static Result<String, CompileError> compileRootSegment(String segment, List<String> namespace) {
        if (segment.startsWith("package ")) return new Ok<>("");

        String stripped = segment.strip();
        if (stripped.startsWith("import ")) {
            String right = stripped.substring("import ".length());
            if (right.endsWith(";")) {
                String segmentString = right.substring(0, right.length() - ";".length());
                String[] segmentSplits = segmentString.split(Pattern.quote("."));
                List<String> oldSegments = new ArrayList<>(Arrays.asList(segmentSplits));
                if (oldSegments.size() >= 3 && oldSegments.subList(0, 3).equals(FUNCTIONAL_PACKAGE)) {
                    return new Ok<>("");
                }

                List<String> path = new ArrayList<>();
                for (int i = 0; i < namespace.size(); i++) {
                    path.add("..");
                }

                List<String> newSegments = new ArrayList<>();

                if (!oldSegments.isEmpty()) {
                    String first = oldSegments.getFirst();
                    List<String> slice = oldSegments.subList(1, oldSegments.size());
                    if (first.equals("jvm")) {
                        newSegments.add("windows");
                        newSegments.addAll(slice);
                    } else {
                        newSegments.addAll(oldSegments);
                    }
                }

                path.addAll(newSegments);

                String replaced = String.join("/", path);
                return new Ok<>("#include \"" + replaced + ".h\"\n");
            }
        }

        if (segment.contains("class ")) {
            return createStructRule().generate(new MapNode().withString("name", "Temp"));
        }

        Result<String, CompileError> maybeInterface = compileInterface(segment);
        if (maybeInterface.isOk()) return maybeInterface;

        if (segment.contains("record ")) {
            return createStructRule().generate(new MapNode().withString("name", "Temp"));
        }

        return new Err<>(new CompileError("Invalid root segment", new StringContext(segment)));
    }

    private static Result<String, CompileError> compileInterface(String input) {
        return createInterfaceRule().parse(input).flatMapValue(mapNode -> createStructRule().generate(mapNode));
    }

    private static SuffixRule createStructRule() {
        return new SuffixRule(new PrefixRule("struct ", new StringRule("name")), " {\n};\n");
    }

    private static Rule createInterfaceRule() {
        Rule modifiers = new StringRule("modifiers");
        Rule name = new StringRule("name");
        Rule superType = new StringRule("super-type");

        Rule withSuperType = new InfixRule(name, "extends ", superType);
        Rule beforeContent = new OrRule(List.of(withSuperType, superType));
        return new InfixRule(modifiers, "interface ", new InfixRule(beforeContent, "{", new StringRule("with-end")));
    }
}
