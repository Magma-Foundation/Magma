package magma;

import magma.api.Tuple;
import magma.api.result.Err;
import magma.api.result.Ok;
import magma.api.result.Result;
import magma.api.result.Results;
import magma.api.collect.stream.Streams;
import magma.compile.CompileException;
import magma.compile.Error_;
import magma.compile.lang.JavaAnnotator;
import magma.compile.lang.JavaLang;
import magma.compile.lang.JavaToMagmaGenerator;
import magma.compile.lang.MagmaAnnotator;
import magma.compile.lang.MagmaFormatter;
import magma.compile.lang.MagmaLang;
import magma.compile.annotate.State;
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

    public static final Path TARGET_DIRECTORY = Paths.get(".", "magmac", "src", "magma");
    public static final Path SOURCE_DIRECTORY = Paths.get(".", "magmac", "src", "java");
    public static final Path DEBUG_DIRECTORY = Paths.get(".", "magmac", "debug", "java-mgs");

    public static void main(String[] args) {
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
        var sources = Files.walk(SOURCE_DIRECTORY)
                .filter(value -> value.toString().endsWith(".java"))
                .filter(Files::isRegularFile)
                .toList();

        var sourceTrees = parseSources(sources);
        var targetTrees = new HashMap<List<String>, Node>();
        for (Map.Entry<List<String>, Node> entry : sourceTrees.entrySet()) {
            var location = entry.getKey();
            var namespace = location.subList(0, location.size() - 1);
            var name = location.get(location.size() - 1);
            System.out.println("Generating target: " + String.join(".", namespace) + "." + name);

            var generated = Results.unwrap(generate(entry, sourceTrees)
                    .mapValue(Tuple::left)
                    .mapErr(error -> {
                        writeError(error, location);
                        return new CompileException("Failed to generate: " + String.join(".", location));
                    }));

            var relativizedDebug = createDebug(namespace);
            writeImpl(relativizedDebug.resolve(name + ".output.ast"), generated.toString());
            targetTrees.put(location, generated);
        }

        for (Map.Entry<List<String>, Node> entry : targetTrees.entrySet()) {
            var location = entry.getKey();
            var namespace = location.subList(0, location.size() - 1);
            var name = location.get(location.size() - 1);
            System.out.println("Writing target: " + String.join(".", namespace) + "." + name);

            var targetParent = TARGET_DIRECTORY;
            for (String segment : namespace) {
                targetParent = targetParent.resolve(segment);
            }

            if (!Files.exists(targetParent)) createDirectory(targetParent);
            var target = targetParent.resolve(name + ".mgs");

            Rule rule = MagmaLang.createRootRule();
            var generateResult = rule.fromNode(entry.getValue());
            var generateErrorOptional = JavaOptionals.toNative(generateResult.findErr());
            if (generateErrorOptional.isPresent()) {
                var generateError = generateErrorOptional.get();
                print(generateError, 0);
                writeError(generateError, location);
            }

            writeImpl(target, JavaOptionals.toNative(generateResult.findValue()).orElseThrow(() -> new CompileException("Nothing was generated.")));
        }
    }

    private static Result<Tuple<Node, State>, Error_> generate(Map.Entry<List<String>, Node> entry, Map<List<String>, Node> sourceTrees) {
        var state = new State(JavaSet.fromNative(sourceTrees.keySet(), JavaList::new), new JavaList<>());

        var list = List.of(
                new JavaAnnotator(),
                new JavaToMagmaGenerator(),
                new MagmaAnnotator(),
                new MagmaFormatter()
        );

        var initial = new Tuple<>(entry.getValue(), state);
        return Streams.fromNativeList(list).foldRightToResult(initial, Main::generateImpl);
    }

    private static Result<Tuple<Node, State>, Error_> generateImpl(Tuple<Node, State> tuple, TreeGenerator generator) {
        var node = tuple.left();
        var state1 = tuple.right();

        return generator.generate(node, state1);
    }

    private static Map<List<String>, Node> parseSources(List<Path> sources) throws CompileException {
        var trees = new HashMap<List<String>, Node>();
        for (var source : sources) {
            var relativized = SOURCE_DIRECTORY.relativize(source.getParent());
            var namespace = computeNamespace(relativized);
            var name = computeName(source);

            if(namespace.size() >= 2) {
                var slice = namespace.subList(0, 2);
                if(slice.equals(List.of("magma", "java"))) {
                    continue;
                }
            }

            var result = parseSource(source, namespace, name);
            var error = JavaOptionals.toNative(result.findErr());
            if (error.isPresent()) {
                throw error.get();
            } else {
                var list = new ArrayList<>(namespace);
                list.add(name);

                trees.put(list, JavaOptionals.toNative(result.findValue()).orElseThrow());
            }
        }

        return trees;
    }

    private static Result<Node, CompileException> parseSource(Path source, List<String> namespace, String name) throws CompileException {
        System.out.println("Parsing source: " + SOURCE_DIRECTORY.relativize(source));

        var location = new ArrayList<>(namespace);
        location.add(name);

        var input = readImpl(source);

        return JavaLang.createRootRule().toNode(input).create().match(
                root -> parse(root, namespace, name),
                err -> writeError(err, location));
    }

    private static String computeName(Path source) {
        var fileName = source.getFileName().toString();
        var name = fileName.substring(0, fileName.indexOf('.'));
        return name;
    }

    private static Result<Node, CompileException> parse(Node root, List<String> namespace, String name) {
        try {
            var relativizedDebug = createDebug(namespace);
            writeImpl(relativizedDebug.resolve(name + ".input.ast"), root.toString());
            return new Ok<>(root);
        } catch (CompileException e) {
            return new Err<>(e);
        }
    }

    private static List<String> computeNamespace(Path relativized) {
        return IntStream.range(0, relativized.getNameCount())
                .mapToObj(index -> relativized.getName(index).toString())
                .toList();
    }

    private static Path createDebug(List<String> namespace) throws CompileException {
        var relativizedDebug = DEBUG_DIRECTORY;
        for (String s : namespace) {
            relativizedDebug = relativizedDebug.resolve(s);
        }

        if (!Files.exists(relativizedDebug)) createDirectory(relativizedDebug);
        return relativizedDebug;
    }

    private static Err<Node, CompileException> writeError(Error_ err, List<String> location) {
        try {
            var result = print(err, 0);
            writeImpl(DEBUG_DIRECTORY.resolve("error.xml"), result);
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

    private static Path createDirectory(Path targetParent) throws CompileException {
        try {
            return Files.createDirectories(targetParent);
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
        var messageAttribute = message.isEmpty() ? "" : " message=\"" + message.replace("\"", "&quot;") + "\"";
        var causes = error.findCauses().orElse(Collections.emptyList());

        var escapedContext = error.findContext().orElse("")
                .replace("&", "&amp;")
                .replace("\"", "&quot;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("'", "&apos;")
                .replace("\\n", "\\\\n")
                .replace("\n", "\\n")
                .replace("\\t", "\\\\t")
                .replace("\t", "\\t")
                .replace("\\r", "\\\\r")
                .replace("\r", "\\r");

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

    private static String formatContext(Error_ e, int depth) {
        var actualContext = e.findContext().orElse("");
        if (e.findCauses().isPresent()) return actualContext;

        var spacing = " ".repeat(depth + 1);
        var formatted = actualContext.replace("\n", "\n" + " ".repeat(depth == 0 ? 0 : depth - 1));
        return "\n" + spacing + "---\n" + spacing + formatted + "\n" + spacing + "---";
    }

}
