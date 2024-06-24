package magma;

import magma.api.Tuple;
import magma.api.collect.stream.Streams;
import magma.api.option.None;
import magma.api.option.Option;
import magma.api.option.Some;
import magma.api.result.Err;
import magma.api.result.Ok;
import magma.api.result.Result;
import magma.compile.CompileException;
import magma.compile.Error_;
import magma.compile.annotate.State;
import magma.compile.lang.JavaAnnotator;
import magma.compile.lang.JavaLang;
import magma.compile.lang.JavaToMagmaGenerator;
import magma.compile.lang.MagmaAnnotator;
import magma.compile.lang.MagmaFormatter;
import magma.compile.lang.MagmaLang;
import magma.compile.lang.TreeGenerator;
import magma.compile.rule.Node;
import magma.compile.rule.Rule;
import magma.java.JavaList;
import magma.java.JavaOptionals;
import magma.java.JavaSet;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class Main {
    public static final Path CONFIG_PATH = Paths.get(".", "config.json");

    public static void main(String[] args) {
        var result = run();
        if (result.isPresent()) {
            result.orElsePanic().printStackTrace();
        }
    }

    private static Option<CompileException> run() {
        return buildConfiguration().mapErr(CompileException::new).mapValue(configuration -> {
            var listIOExceptionResult = walkImpl(configuration);
            return listIOExceptionResult.mapErr(CompileException::new)
                    .flatMapValue(sources -> parseSources(sources, configuration)
                            .flatMapValue(sourceTrees -> generateTargets(configuration, sourceTrees)
                                    .mapValue(targetTrees -> writeTargets(targetTrees, configuration))))
                    .match(value -> value, Some::new);
        }).match(value -> value, Some::new);
    }

    private static Result<List<Path>, IOException> walkImpl(Configuration configuration) {
        try {
            //TODO: #14
            //noinspection resource
            return new Ok<>(Files.walk(configuration.sourceDirectory())
                    .filter(value -> value.toString().endsWith(".java"))
                    .filter(Files::isRegularFile)
                    .toList());
        } catch (IOException e) {
            return new Err<>(e);
        }
    }

    private static Result<Configuration, IOException> buildConfiguration() {
        return readConfiguration().mapValue(map -> {
            var sourceDirectory = Paths.get(map.get("sources"));
            var debugDirectory = Paths.get(map.get("debug"));
            var targetDirectory = Paths.get(map.get("targets"));
            return new Configuration(sourceDirectory, targetDirectory, debugDirectory);
        });
    }

    private static Result<Map<List<String>, Node>, CompileException> generateTargets(Configuration configuration, Map<List<String>, Node> sourceTrees) {
        Result<Map<List<String>, Node>, CompileException> targetTrees = new Ok<>(Collections.emptyMap());

        for (Map.Entry<List<String>, Node> entry : sourceTrees.entrySet()) {
            var location = entry.getKey();
            targetTrees = targetTrees.flatMapValue(inner0 -> generateTarget(configuration, sourceTrees, location, entry.getValue()).mapValue(inner1 -> {
                var copy = new HashMap<>(inner0);
                copy.put(location, inner1);
                return copy;
            }));
        }

        return targetTrees;
    }

    private static Result<Node, CompileException> generateTarget(
            Configuration configuration,
            Map<List<String>, Node> sourceTrees,
            List<String> location,
            Node root) {
        var namespace = location.subList(0, location.size() - 1);
        var name = location.get(location.size() - 1);

        System.out.println("Generating target: " + String.join(".", namespace) + "." + name);

        return generate(sourceTrees, root)
                .mapValue(Tuple::left)
                .mapErr(error -> writeError(error, location, configuration))
                .flatMapValue(generated -> {
                    return createDebug(namespace, configuration).flatMapValue(relativizedDebug -> {
                        return writeSafely(relativizedDebug.resolve(name + ".output.ast"), generated.toString())
                                .<Result<Node, CompileException>>map(Err::new)
                                .orElseGet(() -> new Ok<>(generated));
                    });
                });
    }

    private static Option<CompileException> writeTargets(Map<List<String>, Node> targetTrees, Configuration configuration) {
        for (Map.Entry<List<String>, Node> entry : targetTrees.entrySet()) {
            var location = entry.getKey();
            var option = writeTarget(configuration, location, entry.getValue());
            if (option.isPresent()) {
                return option;
            }
        }

        return new None<>();
    }

    private static Option<CompileException> writeTarget(Configuration configuration, List<String> location, Node root) {
        var namespace = location.subList(0, location.size() - 1);
        var name = location.get(location.size() - 1);
        System.out.println("Writing target: " + String.join(".", namespace) + "." + name);

        var targetParent = configuration.targetDirectory();
        for (String segment : namespace) {
            targetParent = targetParent.resolve(segment);
        }

        if (!Files.exists(targetParent)) {
            var result = createDirectory(targetParent);
            if (result.isPresent()) {
                return result;
            }
        }
        var target = targetParent.resolve(name + ".mgs");

        Rule rule = MagmaLang.createRootRule();
        var generateResult = rule.fromNode(root);
        var generateErrorOptional = JavaOptionals.toNative(generateResult.findErr());
        if (generateErrorOptional.isPresent()) {
            var generateError = generateErrorOptional.get();
            print(generateError, 0);

            return new Some<>(writeError(generateError, location, configuration));
        }

        return generateResult.findValue()
                .map(inner -> writeSafely(target, inner))
                .map(option -> option.orElseGet(() -> new CompileException("Nothing was generated.")));
    }

    private static Result<Map<String, String>, IOException> readConfiguration() {
        try {
            var absolutePath = CONFIG_PATH.toAbsolutePath();
            if (Files.exists(CONFIG_PATH)) {
                System.out.println("Found configuration file at '" + absolutePath + "'.");
            } else {
                System.out.printf("Configuration file did not exist and will be created at '%s'.%n", absolutePath);
                Files.writeString(CONFIG_PATH, "{}");
            }

            var configurationString = Files.readString(CONFIG_PATH);
            return new Ok<>(parseConfigurationFromJSON(configurationString));
        } catch (IOException e) {
            return new Err<>(e);
        }
    }

    private static Map<String, String> parseConfigurationFromJSON(String configurationJSON) {
        var map = new HashMap<String, String>();
        var stripped = configurationJSON.strip();
        var lines = stripped
                .substring(1, stripped.length() - 1)
                .split(",");

        for (String line : lines) {
            var separator = line.indexOf(":");
            var left = line.substring(0, separator).strip();
            var right = line.substring(separator + 1).strip();
            var propertyName = left.substring(1, left.length() - 1);
            var propertyValue = right.substring(1, right.length() - 1);
            map.put(propertyName, propertyValue);
        }

        System.out.println("Parsed configuration.");
        System.out.println(map);
        return map;
    }

    private static Result<Tuple<Node, State>, Error_> generate(Map<List<String>, Node> sourceTrees, Node root) {
        var state = new State(JavaSet.fromNative(sourceTrees.keySet(), JavaList::new), new JavaList<>());

        var list = List.of(
                new JavaAnnotator(),
                new JavaToMagmaGenerator(),
                new MagmaAnnotator(),
                new MagmaFormatter()
        );

        var initial = new Tuple<>(root, state);
        return Streams.fromNativeList(list).foldRightToResult(initial, Main::generateImpl);
    }

    private static Result<Tuple<Node, State>, Error_> generateImpl(Tuple<Node, State> tuple, TreeGenerator generator) {
        var node = tuple.left();
        var state1 = tuple.right();

        return generator.generate(node, state1);
    }

    private static Result<Map<List<String>, Node>, CompileException> parseSources(List<Path> sources, Configuration configuration) {
        Result<Map<List<String>, Node>, CompileException> trees = new Ok<>(new HashMap<>());
        for (var source : sources) {
            trees = trees.flatMapValue(inner -> parseSource(configuration, source).mapValue(inner0 -> {
                var copy = new HashMap<>(inner);
                copy.putAll(inner0);
                return copy;
            }));
        }

        return trees;
    }

    private static Result<Map<ArrayList<String>, Node>, CompileException> parseSource(Configuration configuration, Path source) {
        var relativized = configuration.sourceDirectory().relativize(source.getParent());
        var namespace = computeNamespace(relativized);
        var name = computeName(source);

        if (namespace.size() >= 2) {
            var slice = namespace.subList(0, 2);

            // Essentially, we want to skip this package.
            if (slice.equals(List.of("magma", "java"))) {
                return new Ok<>(Collections.emptyMap());
            }
        }

        var location = new ArrayList<>(namespace);
        location.add(name);
        return parseSource(new PathSource(source, namespace, name), configuration)
                .mapValue(value -> Map.of(location, value));
    }

    private static Result<Node, CompileException> parseSource(PathSource pathSource, Configuration configuration) {
        System.out.println("Parsing source: " + configuration.sourceDirectory().relativize(pathSource.path()));

        return readImpl(pathSource.path()).mapValue(input -> {
            return JavaLang.createRootRule().toNode(input).create().match(
                    root -> parse(pathSource, configuration, root),
                    err -> new Err<Node, CompileException>(writeErrorImpl(configuration, pathSource, err)));
        }).match(result -> result, Err::new);
    }

    private static CompileException writeErrorImpl(Configuration configuration, PathSource source, Error_ err) {
        var location = new ArrayList<>(source.namespace());
        location.add(source.name());
        return writeError(err, location, configuration);
    }

    private static Result<Node, CompileException> parse(PathSource pathSource, Configuration configuration, Node root) {
        return createDebug(pathSource.namespace(), configuration).flatMapValue(relativizedDebug -> {
            return writeSafely(relativizedDebug.resolve(pathSource.name() + ".input.ast"), root.toString())
                    .<Result<Node, CompileException>>map(Err::new)
                    .orElseGet(() -> new Ok<>(root));
        });
    }

    private static String computeName(Path source) {
        var fileName = source.getFileName().toString();
        return fileName.substring(0, fileName.indexOf('.'));
    }

    private static List<String> computeNamespace(Path relativized) {
        return IntStream.range(0, relativized.getNameCount())
                .mapToObj(index -> relativized.getName(index).toString())
                .toList();
    }

    private static Result<Path, CompileException> createDebug(List<String> namespace, Configuration config) {
        var relativizedDebug = config.debugDirectory();
        for (String s : namespace) {
            relativizedDebug = relativizedDebug.resolve(s);
        }

        if (!Files.exists(relativizedDebug)) {
            var directoryError = createDirectory(relativizedDebug);
            if (directoryError.isPresent()) {
                return new Err<>(directoryError.orElsePanic());
            }
        }

        return new Ok<>(relativizedDebug);
    }

    private static CompileException writeError(Error_ err, List<String> location, Configuration config) {
        var result = print(err, 0);
        return writeSafely(config.debugDirectory().resolve("error.xml"), result)
                .orElseGet(() -> new CompileException(String.join(".", location)));
    }

    private static Result<String, CompileException> readImpl(Path source) {
        try {
            return new Ok<>(Files.readString(source));
        } catch (IOException e) {
            return new Err<>(new CompileException("Failed to read input: " + source, e));
        }
    }

    private static Option<CompileException> createDirectory(Path targetParent) {
        try {
            Files.createDirectories(targetParent);
            return new None<>();
        } catch (IOException e) {
            return new Some<>(new CompileException("Failed to make parent.", e));
        }
    }

    private static Option<CompileException> writeSafely(Path target, String csq) {
        try {
            Files.writeString(target, csq);
            return new None<>();
        } catch (IOException e) {
            return new Some<>(new CompileException("Cannot write.", e));
        }
    }

    private static String print(Error_ error, int depth) {
        var context = formatContext(error, depth);

        var anyMessage = error.findMessage();
        anyMessage.ifPresent(s -> System.err.println(" ".repeat(depth) + depth + " = " + s + " " + context));

        var message = error.findMessage().orElse("");
        var replaced = escape(message);

        var messageAttribute = message.isEmpty() ? "" : " message=\"" + replaced + "\"";
        var causes = error.findCauses().orElse(Collections.emptyList());

        var escapedContext = escape(error.findContext().orElse(""));

        var formattedContext = "\n" + "\t".repeat(depth) + escapedContext;
        if (causes.isEmpty()) {
            return "\n" + "\t".repeat(depth) + "<child" + messageAttribute + ">" + formattedContext + "</child>";
        }

        var contextAttribute = escapedContext.isEmpty() ? "" : " context=\"" + escapedContext + "\"";
        if (causes.size() == 1) {
            return "\n" + "\t".repeat(depth) + "<parent" + messageAttribute + contextAttribute + ">" + print(causes.get(0), depth + 1) + "</parent>";
        }

        var list = causes.stream()
                .sorted(Comparator.comparingInt(Error_::calculateDepth))
                .toList();

        var builder = new StringBuilder();
        for (var cause : list) {
            var result = print(cause, depth + 1);
            builder.append(result);
        }

        return "\n" + "\t".repeat(depth) + "<collection" + messageAttribute + contextAttribute + ">" + builder + "</collection>";
    }

    private static String escape(String value) {
        return value.replace("&", "&amp;")
                .replace("\"", "&quot;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("'", "&apos;")
                .replace("\\", "\\\\")
                .replace("\n", "\\n")
                .replace("\t", "\\t")
                .replace("\r", "\\r");
    }

    private static String formatContext(Error_ e, int depth) {
        var actualContext = e.findContext().orElse("");
        if (e.findCauses().isPresent()) return actualContext;

        var spacing = " ".repeat(depth + 1);
        var formatted = actualContext.replace("\n", "\n" + " ".repeat(depth == 0 ? 0 : depth - 1));
        return "\n" + spacing + "---\n" + spacing + formatted + "\n" + spacing + "---";
    }
}
