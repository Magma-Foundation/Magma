package magma;

import magma.api.Tuple;
import magma.api.collect.Map;
import magma.api.collect.stream.Collectors;
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
import magma.java.JavaMap;
import magma.java.JavaOptionals;
import magma.java.JavaResults;
import magma.java.JavaSet;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static magma.java.JavaResults.$;
import static magma.java.JavaResults.$Void;

public record Application(Configuration config) {
    static Option<CompileException> createDirectory(Path targetParent) {
        try {
            Files.createDirectories(targetParent);
            return new None<>();
        } catch (IOException e) {
            return new Some<>(new CompileException("Failed to make parent.", e));
        }
    }

    static String print(Error_ error, int depth) {
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

    static Option<CompileException> writeSafely(Path target, String csq) {
        try {
            Files.writeString(target, csq);
            return new None<>();
        } catch (IOException e) {
            return new Some<>(new CompileException("Cannot write.", e));
        }
    }

    static Result<Tuple<Node, State>, Error_> generate(Map<Unit, Node> sourceTrees, Node root) {
        var state = new State(sourceTrees.keyStream().collect(JavaSet.collecting()), new JavaList<>());

        var list = List.of(
                new JavaAnnotator(),
                new JavaToMagmaGenerator(),
                new MagmaAnnotator(),
                new MagmaFormatter()
        );

        var initial = new Tuple<>(root, state);
        return Streams.fromNativeList(list).foldRightToResult(initial, Application::generateImpl);
    }

    private static Result<Tuple<Node, State>, Error_> generateImpl(Tuple<Node, State> tuple, TreeGenerator generator) {
        var node = tuple.left();
        var state1 = tuple.right();

        return generator.generate(node, state1);
    }

    Option<CompileException> run() {
        return $Void(() -> {
            var sources = $(findSources().mapErr(CompileException::new));
            var sourceTrees = $(parseSources(sources));
            var targetTrees = $(generateTargets(sourceTrees));
            $(writeTargets(targetTrees));
        });
    }

    Result<List<Path>, IOException> findSources() {
        //noinspection resource
        return $(() -> Files.walk(config.sourceDirectory())
                .filter(value -> value.toString().endsWith(".java"))
                .filter(Files::isRegularFile)
                .toList());
    }

    Result<Map<Unit, Node>, CompileException> generateTargets(Map<Unit, Node> sourceTrees) {
        return sourceTrees.streamEntries()
                .map(entry -> generateTarget(sourceTrees, entry))
                .collect(Collectors.exceptionally(JavaMap.collecting()));
    }

    Result<Tuple<Unit, Node>, CompileException> generateTarget(
            Map<Unit, Node> sourceTrees,
            Tuple<Unit, Node> entry) {
        return $(() -> {
            var source = entry.left();
            var right = entry.right();

            var namespace = source.computeNamespace();
            var name = source.computeName();

            System.out.println("Generating target: " + String.join(".", namespace) + "." + name);

            var generated = JavaResults.$(generate(sourceTrees, right)
                    .mapValue(Tuple::left)
                    .mapErr(error -> writeError(error, source)));

            var debug = $(createDebugDirectory(namespace));
            var debugTarget = debug.resolve(name + ".output.ast");

            $(writeSafely(debugTarget, generated.toString()));
            return new Tuple<>(source, generated);
        });
    }

    Option<CompileException> writeTargets(Map<Unit, Node> targetTrees) {
        return targetTrees.streamEntries()
                .map(entry -> writeTarget(entry.left(), entry.right()))
                .flatMap(Streams::fromOption)
                .head();
    }

    Option<CompileException> writeTarget(Unit source, Node root) {
        var namespace = source.computeNamespace();
        var name = source.computeName();

        System.out.println("Writing target: " + String.join(".", namespace) + "." + name);

        var targetParent = config.targetDirectory();
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

            return new Some<>(writeError(generateError, source));
        }

        return generateResult.findValue()
                .map(inner -> writeSafely(target, inner))
                .map(option -> option.orElseGet(() -> new CompileException("Nothing was generated.")));
    }

    Result<Map<Unit, Node>, CompileException> parseSources(List<Path> sources) {
        Result<Map<Unit, Node>, CompileException> trees = new Ok<>(new JavaMap<>());
        for (var source : sources) {
            trees = trees.flatMapValue(inner -> parseSource(new PathUnit(config.sourceDirectory(), source)).mapValue(inner::putAll));
        }

        return trees;
    }

    Result<Map<Unit, Node>, CompileException> parseSource(Unit source) {
        var namespace = source.computeNamespace();
        if (namespace.size() >= 2) {
            var slice = namespace.subList(0, 2);

            // Essentially, we want to skip this package.
            if (slice.equals(List.of("magma", "java"))) {
                return new Ok<>(new JavaMap<>());
            }
        }

        System.out.println("Parsing source: " + source);

        return source.read().mapValue(input -> JavaLang.createRootRule().toNode(input).create().match(
                root -> parse(source, root),
                err -> new Err<Node, CompileException>(writeError(err, source))))
                .<Result<Node, CompileException>>match(result -> result, Err::new).mapValue(value -> new JavaMap<>(java.util.Map.of(source, value)));
    }

    Result<Node, CompileException> parse(Unit unit, Node root) {
        return createDebugDirectory(unit.computeNamespace()).flatMapValue(relativizedDebug -> writeSafely(relativizedDebug.resolve(unit.computeName() + ".input.ast"), root.toString())
                .<Result<Node, CompileException>>map(Err::new)
                .orElseGet(() -> new Ok<>(root)));
    }

    CompileException writeError(Error_ err, Unit location) {
        var result = print(err, 0);
        return writeSafely(config.debugDirectory().resolve("error.xml"), result)
                .orElseGet(() -> new CompileException(location.toString()));
    }

    Result<Path, CompileException> createDebugDirectory(List<String> namespace) {
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
}