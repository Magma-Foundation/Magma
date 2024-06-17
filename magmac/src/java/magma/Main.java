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
import java.util.Optional;

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

        for (var source : sources) {
            var error = compileSource(source);
            if (error.isPresent()) {
                throw error.get();
            }
        }
    }

    private static Optional<CompileException> compileSource(Path source) throws CompileException {
        var relativized = SOURCE_DIRECTORY.relativize(source.getParent());
        System.out.println("Compiling source: " + SOURCE_DIRECTORY.relativize(source));

        var targetParent = TARGET_DIRECTORY.resolve(relativized);
        var fileName = source.getFileName().toString();
        var name = fileName.substring(0, fileName.indexOf('.'));
        var target = targetParent.resolve(name + ".mgs");
        var input = readImpl(source);

        var parseResult = JavaLang.createRootRule().toNode(input);
        return parseResult.create().match(
                root -> handle(root, targetParent, relativized, name, target),
                Main::getCompileException);
    }

    private static Optional<CompileException> getCompileException(Error_ err) {
        try {
            var result = print(err, 0);
            writeImpl(DEBUG_DIRECTORY.resolve("error.xml"), result);
            return Optional.of(new CompileException());
        } catch (CompileException e) {
            return Optional.of(e);
        }
    }

    private static Optional<CompileException> handle(Node root, Path targetParent, Path relativized, String name, Path target) {
        try {
            if (!Files.exists(targetParent)) createDirectory(targetParent);

            var relativizedDebug = DEBUG_DIRECTORY.resolve(relativized);
            if (!Files.exists(relativizedDebug)) createDirectory(relativizedDebug);

            writeImpl(relativizedDebug.resolveSibling(name + ".input.ast"), root.toString());
            var generated = generate(root);
            writeImpl(relativizedDebug.resolveSibling(name + ".output.ast"), generated.toString());

            Rule rule = MagmaLang.createRootRule();
            var generateResult = rule.fromNode(generated);
            var generateError = generateResult.findErr();
            generateError.ifPresent(error -> print(error, 0));

            writeImpl(target, generateResult.findValue().orElseThrow(() -> new CompileException("Nothing was generated.")));
            return Optional.empty();
        } catch (CompileException e) {
            return Optional.of(e);
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
                .replace("'", "&apos;");

        var formattedContext = "\n" + escapedContext;
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
        var formatted = actualContext.replace("\n", "\n" + " ".repeat(depth - 1));
        return "\n" + spacing + "---\n" + spacing + formatted + "\n" + spacing + "---";
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
