package magma;

import magma.collect.list.Lists;
import magma.option.None;
import magma.option.Option;
import magma.option.Options;
import magma.option.Some;

import java.io.IOException;
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
            relatives.add(runWithSource(source));
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

    private static Path runWithSource(Path source) throws IOException, CompileException {
        Path relative = SOURCE_DIRECTORY.relativize(source);
        Path parent = relative.getParent();

        ArrayList<String> namespace = new ArrayList<>();
        for (int i = 0; i < parent.getNameCount(); i++) {
            namespace.add(parent.getName(i).toString());
        }

        String nameWithExt = relative.getFileName().toString();
        String name = nameWithExt.substring(0, nameWithExt.lastIndexOf("."));

        String input = Files.readString(source);
        String output = compile(input, namespace, name);

        Path targetParent = TARGET_DIRECTORY.resolve(parent);
        if (!Files.exists(targetParent)) Files.createDirectories(targetParent);

        Path target = targetParent.resolve(name + ".c");
        Files.writeString(target, output);

        Path header = targetParent.resolve(name + ".h");
        Files.writeString(header, "");

        return TARGET_DIRECTORY.relativize(target);
    }

    private static String compile(String input, List<String> namespace, String name) throws CompileException {
        List<String> segments = divideStatements(input);

        StringBuilder builder = new StringBuilder();
        for (String segment : segments) {
            builder.append(compileRootSegment(segment, namespace));
        }

        String output = builder.toString();
        if (namespace.equals(List.of("magma")) && name.equals("Main")) {
            return output + "int main(){\n\treturn 0;\n}\n";
        }

        return output;
    }

    private static List<String> divideStatements(String input) {
        DividingState current = new MutableDividingState();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            current = divideStatementChar(current, c);
        }

        return Lists.toNative(current.advance().segments());
    }

    private static DividingState divideStatementChar(DividingState current, char c) {
        DividingState appended = current.append(c);
        if (c == ';' && appended.isLevel()) return appended.advance();
        if (c == '{') return appended.enter();
        if (c == '}') return appended.exit();
        return appended;
    }

    private static String compileRootSegment(String input, List<String> namespace) throws CompileException {
        List<Rule> rules = List.of(
                new Rule() {
                    @Override
                    public Option<String> compile(String input) {
                        return compilePackage(input);
                    }
                },
                new Rule() {
                    @Override
                    public Option<String> compile(String input) {
                        return compileImport(input, namespace);
                    }
                },
                new Rule() {
                    @Override
                    public Option<String> compile(String input) {
                        return compileClass(input);
                    }
                },
                new Rule() {
                    @Override
                    public Option<String> compile(String input) {
                        return compileInterface(input);
                    }

                },
                new Rule() {
                    @Override
                    public Option<String> compile(String input) {
                        if (input.contains("record ")) {
                            return generateStruct();
                        }

                        return new None<>();
                    }
                }
        );

        for (Rule rule : rules) {
            Optional<String> maybe = Options.toNative(rule.compile(input));
            if (maybe.isPresent()) return maybe.get();
        }

        throw new CompileException("Invalid root segment", input);
    }

    private static Option<String> compilePackage(String input) {
        if (input.startsWith("package ")) return generateEmpty();
        return new None<>();
    }

    private static Option<String> compileClass(String input) {
        if (input.contains("class ")) {
            return generateStruct();
        }
        return new None<>();
    }

    private static Option<String> generateStruct() {
        return new Some<>("struct Temp {\n};\n");
    }

    private static Option<String> compileInterface(String input) {
        if (input.contains("interface ")) {
            return generateStruct();
        }
        return new None<>();
    }

    private static Option<String> compileImport(String input, List<String> thisNamespace) {
        if (!input.strip().startsWith("import ")) return new None<>();

        String right = input.strip().substring("import ".length());
        if (!right.endsWith(";")) return new None<>();

        String namespaceString = right.substring(0, right.length() - ";".length());
        List<String> requestedNamespace = Arrays.asList(namespaceString.split(Pattern.quote(".")));
        if (requestedNamespace.size() >= 3 && requestedNamespace.subList(0, 3).equals(List.of("java", "util", "function"))) {
            return generateEmpty();
        }

        ArrayList<String> copy = new ArrayList<>();
        for (int i = 0; i < thisNamespace.size(); i++) {
            copy.add("..");
        }
        copy.addAll(requestedNamespace);

        String joined = String.join("/", copy);
        return new Some<>("#include \"" +
                joined +
                ".h\"\n");
    }

    private static Some<String> generateEmpty() {
        return new Some<>("");
    }
}
