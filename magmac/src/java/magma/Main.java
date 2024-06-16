package magma;

import magma.compile.CompileException;
import magma.compile.Error_;
import magma.compile.lang.ClassSplitter;
import magma.compile.lang.Generator;
import magma.compile.lang.JavaLang;
import magma.compile.lang.JavaNormalizer;
import magma.compile.lang.MagmaFormatter;
import magma.compile.lang.MagmaGenerator;
import magma.compile.lang.MagmaLang;
import magma.compile.lang.PackageRemover;
import magma.compile.rule.Node;
import magma.compile.rule.Rule;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class Main {

    public static final Path TARGET_DIRECTORY = Paths.get(".", "magmac", "src", "magma");
    public static final Path SOURCE_DIRECTORY = Paths.get(".", "magmac", "src", "java");
    public static final Path DEBUG_DIRECTORY = Paths.get(".", "magmac", "debug", "java-mgs");

    public static void main(String[] args) {
        try {
            var sources = Files.walk(SOURCE_DIRECTORY)
                    .filter(value -> value.toString().endsWith(".java"))
                    .toList();

            for (var source : sources) {
                try {
                    compileSource(source);
                } catch (CompileException e) {
                    throw new CompileException(source.toAbsolutePath().toString(), e);
                }
            }
        } catch (IOException | CompileException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }

    private static void compileSource(Path source) throws CompileException {
        var relativized = SOURCE_DIRECTORY.relativize(source.getParent());
        var targetParent = TARGET_DIRECTORY.resolve(relativized);
        var fileName = source.getFileName().toString();
        var name = fileName.substring(0, fileName.indexOf('.'));
        var target = targetParent.resolve(name + ".mgs");
        var input = readImpl(source);

        var parseResult = JavaLang.createRootRule().toNode(input);
        var parseError = parseResult.findError();

        if (parseError.isPresent()) {
            var result = print(parseError.get(), 0);
            writeImpl(DEBUG_DIRECTORY.resolve("error.xml"), result);
            throw new CompileException();
        }

        var root = parseResult.create();
        if (root.isPresent()) {
            if (!Files.exists(targetParent)) createDirectory(targetParent);

            var relativizedDebug = DEBUG_DIRECTORY.resolve(relativized);
            if (!Files.exists(relativizedDebug)) createDirectory(relativizedDebug);

            writeImpl(relativizedDebug.resolveSibling(name + ".input.ast"), root.toString());
            var generated = generate(root.get());
            writeImpl(relativizedDebug.resolveSibling(name + ".output.ast"), generated.toString());

            Rule rule = MagmaLang.createRootRule();
            var generateResult = rule.fromNode(generated);
            var generateError = generateResult.findErr();
            generateError.ifPresent(error -> print(error, 0));

            writeImpl(target, generateResult.findValue().orElseThrow(() -> new CompileException("Nothing was generated.")));
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

    private static String print(Error_ e, int depth) {
        var actualContext = e.findContext().orElse("");
        var index = actualContext.indexOf('\n');
        String context;
        if (e.findCauses().isEmpty())
            context = "\n" + "|\t".repeat(depth + 1) + "---\n" + "|\t".repeat(depth + 1) + actualContext.replace("\n", "\n" + "|\t".repeat(depth - 1)) + "\n" + "|\t".repeat(depth + 1) + "---";
        else {
            if (index == -1) context = actualContext;
            else context = actualContext.substring(0, index);
        }

        var message = e.findMessage();
        message.ifPresent(s -> System.err.println("|\t".repeat(depth) + depth + " = " + s + " " + context));

        var s = e.findMessage().orElse("");
        var s1 = s.isEmpty() ? "" : " message=\"" + s.replace("\"", "&quot;") + "\"";
        var causes = e.findCauses().orElse(Collections.emptyList());
        if (causes.isEmpty()) {
            var context1 = e.findContext().orElse("")
                    .replace("&", "&amp;")
                    .replace("\"", "&quot;")
                    .replace("<", "&lt;")
                    .replace(">", "&gt;")
                    .replace("'", "&apos;");

            return "\n" + "\t".repeat(depth) + "<child" + s1 + ">\n" + context1 + "</child>";
        }
        if (causes.size() > 1) {
            var list = causes.stream()
                    .sorted(Comparator.comparingInt(Error_::calculateDepth))
                    .toList();

            var builder = new StringBuilder();
            for (Error_ cause : list) {
                var result = print(cause, depth + 1);
                builder.append(result);
            }
            return "\n" + "\t".repeat(depth) + "<collection" + s1 + ">" + builder + "</collection>";
        } else {
            return "\n" + "\t".repeat(depth) + "<parent" + s1 + ">" + print(causes.get(0), depth + 1) + "</parent>";
        }
    }

    private static String escape(String s) {
        return s;
    }

    private static Node generate(Node root) {
        var list = Arrays.asList(
                new PackageRemover(),
                new ClassSplitter(),
                new JavaNormalizer(),
                new MagmaGenerator(),
                new MagmaFormatter()
        );

        Node acc = root;
        for (Generator generator : list) {
            acc = generator.generate(acc, -1);
        }
        return acc;
    }
}
