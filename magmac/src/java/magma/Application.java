package magma;

import magma.api.Tuple;
import magma.api.collect.Map;
import magma.api.collect.stream.ExceptionalCollector;
import magma.api.collect.stream.Stream;
import magma.api.collect.stream.Streams;
import magma.api.option.None;
import magma.api.option.Option;
import magma.api.option.Some;
import magma.api.result.Err;
import magma.api.result.Ok;
import magma.api.result.Result;
import magma.compile.CompileException;
import magma.compile.Error_;
import magma.compile.annotate.ImmutableState;
import magma.compile.lang.CompoundVisitor;
import magma.compile.lang.JavaLang;
import magma.compile.lang.MagmaLang;
import magma.compile.lang.VisitingGenerator;
import magma.compile.lang.Visitor;
import magma.compile.lang.java.ClassNormalizer;
import magma.compile.lang.java.ConstructorNormalizer;
import magma.compile.lang.java.FilteringVisitor;
import magma.compile.lang.java.InterfaceNormalizer;
import magma.compile.lang.java.LambdaNormalizer;
import magma.compile.lang.java.MethodNormalizer;
import magma.compile.lang.java.MethodReferenceNormalizer;
import magma.compile.lang.java.BlockNormalizer;
import magma.compile.lang.java.RecordNormalizer;
import magma.compile.lang.magma.FunctionOptimizer;
import magma.compile.lang.magma.BlockFormatter;
import magma.compile.rule.Node;
import magma.compile.rule.Rule;
import magma.java.JavaList;
import magma.java.JavaMap;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static magma.java.JavaResults.$;
import static magma.java.JavaResults.$Option;
import static magma.java.JavaResults.$Result;
import static magma.java.JavaResults.$Void;

public record Application(Configuration config) {
    static Option<CompileException> createDirectory(Path targetParent) {
        try {
            Files.createDirectories(targetParent);
            return None.None();
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
            return None.None();
        } catch (IOException e) {
            return new Some<>(new CompileException("Cannot write.", e));
        }
    }

    private static Stream<Visitor> streamNormalizers() {
        return Streams.fromNativeList(List.of(
                new FilteringVisitor("block", new BlockNormalizer()),
                new FilteringVisitor("record", new RecordNormalizer()),
                new FilteringVisitor("interface", new InterfaceNormalizer()),
                new FilteringVisitor("class", new ClassNormalizer()),
                new FilteringVisitor("method", new MethodNormalizer()),
                new FilteringVisitor("constructor", new ConstructorNormalizer()),
                new FilteringVisitor("lambda", new LambdaNormalizer()),
                new FilteringVisitor("method-reference", new MethodReferenceNormalizer())
        ));
    }

    private static Result<Rule, CompileException> findRootRule(String platform) {
        if (platform.equals("java")) {
            return new Ok<>(JavaLang.createRootRule());
        }

        if (platform.equals("mgs")) {
            return new Ok<>(MagmaLang.createRootRule());
        }

        return new Err<>(new CompileException("Unknown platform: " + platform));
    }

    Option<CompileException> run() {
        return config.streamBuilds()
                .map(this::compile)
                .flatMap(Streams::fromOption)
                .head();
    }

    private Option<CompileException> compile(Build build) {
        return $Void(() -> {
            var sources = $Result(findSources(build.sourceDirectory()).mapErr(CompileException::new));
            var sourceTrees = $Result(parseSources(build, sources));
            var targetTrees = $Result(generateTargets(build, sourceTrees));
            $Option(writeTargets(build, targetTrees));
        });
    }

    Result<List<Path>, IOException> findSources(BuildSet buildSet) {
        return $(() -> {
            //noinspection resource
            return Files.walk(buildSet.location())
                    .filter(value -> value.toString().endsWith("." + buildSet.platform()))
                    .filter(Files::isRegularFile)
                    .toList();
        });
    }

    Result<Map<Unit, Node>, CompileException> generateTargets(Build build, Map<Unit, Node> sourceTrees) {
        return sourceTrees.streamEntries()
                .map(entry -> generateTarget(build, sourceTrees, entry))
                .collect(new ExceptionalCollector<Map<Unit, Node>, CompileException, Tuple<Unit, Node>>(JavaMap.collecting()));
    }

    Result<Tuple<Unit, Node>, CompileException> generateTarget(Build build, Map<Unit, Node> sourceTrees, Tuple<Unit, Node> entry) {
        return $(() -> {
            var source = entry.left();
            var right = entry.right();

            var namespace = source.computeNamespace();
            var name = source.computeName();

            System.out.println("Generating target: " + String.join(".", namespace) + "." + name);

            var rootGenerator = new CompoundGenerator(List.of(
                    new VisitingGenerator(new CompoundVisitor(streamNormalizers().collect(JavaList.collecting()))),
                    new VisitingGenerator(new CompoundVisitor(streamOptimizers().collect(JavaList.collecting()))),
                    new VisitingGenerator(new CompoundVisitor(streamFormatters().collect(JavaList.collecting())))
            ));

            var generated = $Result(rootGenerator
                    .generate(right, new ImmutableState())
                    .mapValue(Tuple::left)
                    .mapErr(error -> writeError(build, error, source)));

            var debug = $Result(createDebugDirectory(build, namespace));
            var debugTarget = debug.resolve(name + ".output.ast");

            $Option(writeSafely(debugTarget, generated.toString()));
            return new Tuple<>(source, generated);
        });
    }

    private Stream<Visitor> streamOptimizers() {
        return Streams.of(
                new FilteringVisitor("function", new FunctionOptimizer())
        );
    }

    private Stream<Visitor> streamFormatters() {
        return Streams.of(
                new FilteringVisitor("block", new BlockFormatter())
        );
    }

    Option<CompileException> writeTargets(Build build, Map<Unit, Node> targetTrees) {
        return targetTrees.streamEntries()
                .map(entry -> writeTarget(build, entry.left(), entry.right()))
                .flatMap(Streams::fromOption)
                .head();
    }

    Option<CompileException> writeTarget(Build build, Unit source, Node root) {
        var namespace = source.computeNamespace();
        var name = source.computeName();

        System.out.println("Writing target: " + String.join(".", namespace) + "." + name);

        var targetSet = build.targetDirectory();
        var targetParent = targetSet.location();
        for (String segment : namespace) {
            targetParent = targetParent.resolve(segment);
        }

        if (!Files.exists(targetParent)) {
            var result = createDirectory(targetParent);
            if (result.isPresent()) {
                return result;
            }
        }

        var target = targetParent.resolve(name + "." + targetSet.platform());

        return findRootRule(targetSet.platform()).mapValue(rootRule -> rootRule.fromNode(root).match(value -> writeSafely(target, value), err -> {
            print(err, 0);

            return new Some<>(writeError(build, err, source));
        })).match(inner -> inner, Some::new);
    }

    Result<Map<Unit, Node>, CompileException> parseSources(Build build, List<Path> sources) {
        return new JavaList<>(sources)
                .stream()
                .map(source -> new PathUnit(build.sourceDirectory().location(), source))
                .map((unit) -> parseSource(build, unit))
                .flatMap(Streams::fromOption)
                .collect(new ExceptionalCollector<Map<Unit, Node>, CompileException, Tuple<Unit, Node>>(JavaMap.collecting()));
    }

    Option<Result<Tuple<Unit, Node>, CompileException>> parseSource(Build build, Unit source) {
        var namespace = source.computeNamespace();
        if (namespace.size() >= 2) {
            var slice = namespace.subList(0, 2);

            // Essentially, we want to skip this package.
            if (slice.equals(List.of("magma", "java")) || slice.equals(List.of("magma", "lang"))) {
                return None.None();
            }
        }

        System.out.println("Parsing source: " + source);
        return new Some<>(source.read().mapValue(input -> parseWithInput(build, source, input))
                .<Result<Node, CompileException>>match(result -> result, Err::new)
                .mapValue(value -> new Tuple<>(source, value)));
    }

    private Result<Node, CompileException> parseWithInput(Build build, Unit source, String input) {
        return $(() -> {
            var s = $Result(findRootRule(build.sourceDirectory().platform()));
            return $Result(s.toNode(input).create().match(
                    root -> parse(build, source, root),
                    err -> new Err<>(writeError(build, err, source))));
        });
    }

    Result<Node, CompileException> parse(Build build, Unit unit, Node root) {
        return createDebugDirectory(build, unit.computeNamespace())
                .flatMapValue(relativizedDebug -> writeSafely(relativizedDebug.resolve(unit.computeName() + ".input.ast"), root.toString())
                        .<Result<Node, CompileException>>map(Err::new)
                        .orElseGet(() -> new Ok<>(root)));
    }

    CompileException writeError(Build build, Error_ err, Unit location) {
        var result = print(err, 0);

        return $Void(() -> {
            var debugDirectory = $Result(createDebugDirectory(build, Collections.emptyList()));
            var errorPath = debugDirectory.resolve("error.xml");
            $Option(writeSafely(errorPath, result));
        }).orElseGet(() -> new CompileException(location.toString()));
    }

    Result<Path, CompileException> createDebugDirectory(Build build, List<String> namespace) {
        var relativizedDebug = build.debugDirectory();
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