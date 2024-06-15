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

public class Main {

    public static final Path TARGET_DIRECTORY = Paths.get(".", "magmac", "src", "magma");
    public static final Path SOURCE_DIRECTORY = Paths.get(".", "magmac", "src", "java");

    public static void main(String[] args) {
        try {
            var sources = Files.walk(SOURCE_DIRECTORY)
                    .filter(value -> value.toString().endsWith(".java"))
                    .toList();

            for (var source : sources) {
                compileSource(source);
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
            print(parseError.get(), 0);
            throw new CompileException();
        }

        var root = parseResult.create();
        if (root.isPresent()) {
            if (!Files.exists(targetParent)) createDirectory(targetParent);

            var relativizedDebug = Paths.get(".", "magmac", "debug", "java-mgs").resolve(relativized);
            if (!Files.exists(relativizedDebug)) createDirectory(relativizedDebug);

            writeImpl(relativizedDebug.resolveSibling(name + ".input.ast"), root.toString());
            var generated = generate(root.get());
            writeImpl(relativizedDebug.resolveSibling(name + ".output.ast"), generated.toString());

            Rule rule = MagmaLang.createRootRule();
            var generateResult = rule.fromNode(generated);
            var generateError = generateResult.findErr();
            generateError.ifPresent(error -> print(error, 0));

            writeImpl(target, generateResult.findValue().orElseThrow());
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

    private static void print(Error_ e, int depth) {
        var actualContext = e.findContext().orElse("");
        var index = actualContext.indexOf('\n');
        String context;
        if (e.findCauses().isEmpty()) context = "\n---\n" + actualContext + "\n---\n";
        else {
            if (index == -1) context = actualContext;
            else context = actualContext.substring(0, index);
        }

        var message = e.findMessage();
        message.ifPresent(s -> System.err.println("\t".repeat(depth) + depth + " = " + s + " " + context));

        var causes = e.findCauses().orElse(Collections.emptyList());
        if (causes.isEmpty()) {
            return;
        }
        if (causes.size() > 1) {
            for (Error_ cause : causes) {
                print(cause, depth);
            }
        } else {
            print(causes.get(0), depth + 1);
        }
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
