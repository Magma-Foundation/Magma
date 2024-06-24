package magma;

import magma.api.Tuple;
import magma.api.collect.stream.Streams;
import magma.api.result.Err;
import magma.api.result.Ok;
import magma.api.result.Result;
import magma.api.result.Results;
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
        //TODO: #15
        //noinspection TryWithIdenticalCatches
        try {
            run();
        } catch (IOException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        } catch (CompileException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }

    private static void run() throws IOException, CompileException {
        var configuration = buildConfiguration();

        //TODO: #14
        //noinspection resource
        var sources = Files.walk(configuration.sourceDirectory())
                .filter(value -> value.toString().endsWith(".java"))
                .filter(Files::isRegularFile)
                .toList();

        var sourceTrees = parseSources(sources, configuration);
        var targetTrees = generateTargets(sourceTrees, configuration);
        writeTargets(targetTrees, configuration);
    }

    private static Configuration buildConfiguration() throws IOException {
        var map = readConfiguration();
        var sourceDirectory = Paths.get(map.get("sources"));
        var debugDirectory = Paths.get(map.get("debug"));
        var targetDirectory = Paths.get(map.get("targets"));
        return new Configuration(sourceDirectory, targetDirectory, debugDirectory);
    }

    private static HashMap<List<String>, Node> generateTargets(Map<List<String>, Node> sourceTrees, Configuration configuration) throws CompileException {
        var targetTrees = new HashMap<List<String>, Node>();
        for (Map.Entry<List<String>, Node> entry : sourceTrees.entrySet()) {
            generateTarget(configuration, sourceTrees, targetTrees, entry.getKey(), entry.getValue());
        }
        return targetTrees;
    }

    private static void generateTarget(
            Configuration configuration,
            Map<List<String>, Node> sourceTrees,
            Map<List<String>, Node> targetTrees,
            List<String> location,
            Node root
    ) throws CompileException {
        var namespace = location.subList(0, location.size() - 1);
        var name = location.get(location.size() - 1);

        System.out.println("Generating target: " + String.join(".", namespace) + "." + name);

        var generated = Results.unwrap(generate(sourceTrees, root)
                .mapValue(Tuple::left)
                .mapErr(error -> getCompileException(configuration, error, location)));

        var relativizedDebug = createDebug(namespace, configuration);
        writeImpl(relativizedDebug.resolve(name + ".output.ast"), generated.toString());
        targetTrees.put(location, generated);
    }

    private static CompileException getCompileException(Configuration configuration, Error_ error, List<String> location) {
        writeError(error, location, configuration);
        return new CompileException("Failed to generate: " + String.join(".", location));
    }

    private static void writeTargets(Map<List<String>, Node> targetTrees, Configuration configuration) throws CompileException {
        for (Map.Entry<List<String>, Node> entry : targetTrees.entrySet()) {
            var location = entry.getKey();
            writeTarget(configuration, location, entry.getValue());
        }
    }

    private static void writeTarget(Configuration configuration, List<String> location, Node root) throws CompileException {
        var namespace = location.subList(0, location.size() - 1);
        var name = location.get(location.size() - 1);
        System.out.println("Writing target: " + String.join(".", namespace) + "." + name);

        var targetParent = configuration.targetDirectory();
        for (String segment : namespace) {
            targetParent = targetParent.resolve(segment);
        }

        if (!Files.exists(targetParent)) createDirectory(targetParent);
        var target = targetParent.resolve(name + ".mgs");

        Rule rule = MagmaLang.createRootRule();
        var generateResult = rule.fromNode(root);
        var generateErrorOptional = JavaOptionals.toNative(generateResult.findErr());
        if (generateErrorOptional.isPresent()) {
            var generateError = generateErrorOptional.get();
            print(generateError, 0);
            writeError(generateError, location, configuration);
        }

        writeImpl(target, JavaOptionals.toNative(generateResult.findValue()).orElseThrow(() -> new CompileException("Nothing was generated.")));
    }

    private static Map<String, String> readConfiguration() throws IOException {
        var absolutePath = CONFIG_PATH.toAbsolutePath();
        if (Files.exists(CONFIG_PATH)) {
            System.out.println("Found configuration file at '" + absolutePath + "'.");
        } else {
            System.out.printf("Configuration file did not exist and will be created at '%s'.%n", absolutePath);
            Files.writeString(CONFIG_PATH, "{}");
        }

        var configurationString = Files.readString(CONFIG_PATH);
        return parseConfigurationFromJSON(configurationString);
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

    private static Map<List<String>, Node> parseSources(List<Path> sources, Configuration configuration) throws CompileException {
        var trees = new HashMap<List<String>, Node>();
        for (var source : sources) {
            parseSource(configuration, source, trees);
        }

        return trees;
    }

    private static void parseSource(Configuration configuration, Path source, HashMap<List<String>, Node> trees) throws CompileException {
        var relativized = configuration.sourceDirectory().relativize(source.getParent());
        var namespace = computeNamespace(relativized);
        var name = computeName(source);

        if (namespace.size() >= 2) {
            var slice = namespace.subList(0, 2);
            if (slice.equals(List.of("magma", "java"))) {
                return;
            }
        }

        var result = parseSource(new PathSource(source, namespace, name), configuration);
        var error = JavaOptionals.toNative(result.findErr());
        if (error.isPresent()) {
            throw error.get();
        } else {
            var list = new ArrayList<>(namespace);
            list.add(name);

            trees.put(list, JavaOptionals.toNative(result.findValue()).orElseThrow());
        }
    }

    private static Result<Node, CompileException> parseSource(PathSource pathSource, Configuration configuration) throws CompileException {
        System.out.println("Parsing source: " + configuration.sourceDirectory().relativize(pathSource.path()));

        var input = readImpl(pathSource.path());
        return JavaLang.createRootRule().toNode(input).create().match(
                root -> parse(pathSource, configuration, root),
                err -> writeErrorImpl(configuration, pathSource, err));
    }

    private static Err<Node, CompileException> writeErrorImpl(Configuration configuration, PathSource source, Error_ err) {
        var location = new ArrayList<>(source.namespace());
        location.add(source.name());
        return writeError(err, location, configuration);
    }

    private static Result<Node, CompileException> parse(PathSource pathSource, Configuration configuration, Node root) {
        try {
            var relativizedDebug = createDebug(pathSource.namespace(), configuration);
            writeImpl(relativizedDebug.resolve(pathSource.name() + ".input.ast"), root.toString());
            return new Ok<>(root);
        } catch (CompileException e) {
            return new Err<>(e);
        }
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

    private static Path createDebug(List<String> namespace, Configuration config) throws CompileException {
        var relativizedDebug = config.debugDirectory();
        for (String s : namespace) {
            relativizedDebug = relativizedDebug.resolve(s);
        }

        if (!Files.exists(relativizedDebug)) createDirectory(relativizedDebug);
        return relativizedDebug;
    }

    private static Err<Node, CompileException> writeError(Error_ err, List<String> location, Configuration config) {
        try {
            var result = print(err, 0);
            writeImpl(config.debugDirectory().resolve("error.xml"), result);
            return new Err<>(new CompileException(String.join(".", location)));
        } catch (CompileException e) {
            return new Err<>(e);
        }
    }

    private static String readImpl(Path source) throws CompileException {
        try {
            return Files.readString(source);
        } catch (IOException e) {
            throw new CompileException("Failed to read input: " + source, e);
        }
    }

    private static void createDirectory(Path targetParent) throws CompileException {
        try {
            Files.createDirectories(targetParent);
        } catch (IOException e) {
            throw new CompileException("Failed to make parent.", e);
        }
    }

    private static void writeImpl(Path target, String csq) throws CompileException {
        try {
            Files.writeString(target, csq);
        } catch (IOException e) {
            throw new CompileException("Cannot write.", e);
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
