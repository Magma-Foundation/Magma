package magma;

import magma.compile.Error_;
import magma.compile.lang.ClassSplitter;
import magma.compile.lang.JavaLang;
import magma.compile.lang.MagmaFormatter;
import magma.compile.lang.MagmaGenerator;
import magma.compile.lang.MagmaLang;
import magma.compile.lang.Generator;
import magma.compile.lang.MethodNormalizer;
import magma.compile.lang.PackageRemover;
import magma.compile.rule.Node;
import magma.compile.rule.Rule;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;

public class Main {
    public static final String CLASS_KEYWORD_WITH_SPACE = "class ";

    public static void main(String[] args) {
        try {
            var source = Paths.get(".", "magmac", "src", "magma", "Main.java");
            var input = Files.readString(source);
            var target = source.resolveSibling("Main.mgs");

            var parseResult = JavaLang.createRootRule().toNode(input);
            var parseError = parseResult.findError();
            parseError.ifPresent(error -> print(error, 0));

            var root = parseResult.create().orElseThrow();

            Files.writeString(source.resolveSibling("Main.input.ast"), root.toString());
            var generated = generate(root);
            Files.writeString(source.resolveSibling("Main.output.ast"), generated.toString());

            Rule rule = MagmaLang.createRootRule();
            var generateResult = rule.fromNode(generated);
            var generateError = generateResult.findErr();
            generateError.ifPresent(error -> print(error, 0));

            Files.writeString(target, generateResult.findValue().orElseThrow());
        } catch (IOException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }

    private static void print(Error_ e, int depth) {
        var actualContext = e.findContext().orElse("");
        var index = actualContext.indexOf('\n');
        var context = index == -1 ? actualContext : actualContext.substring(0, index);

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
                new MethodNormalizer(),
                new MagmaGenerator(),
                new MagmaFormatter()
        );

        Node acc = root;
        for (Generator generator : list) {
            acc = generator.generate(acc);
        }
        return acc;
    }
}
