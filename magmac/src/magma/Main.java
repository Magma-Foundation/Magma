package magma;

import magma.compile.Error_;
import magma.compile.lang.ClassSplitter;
import magma.compile.lang.JavaLang;
import magma.compile.lang.MagmaFormatter;
import magma.compile.lang.MagmaLang;
import magma.compile.lang.MethodMigrator;
import magma.compile.lang.Modifier;
import magma.compile.lang.ModifierAttacher;
import magma.compile.lang.RootTypeRemover;
import magma.compile.rule.Node;
import magma.compile.rule.Rule;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

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
        var message = e.findMessage();
        message.ifPresent(s -> System.err.println(depth + ": " + s));

        var causes = e.findCauses().orElse(Collections.emptyList());
        if (causes.isEmpty()) {
            var context = e.findContext();
            context.ifPresent(s -> System.err.println("\n---\n" + s + "\n---\n"));
        } else {
            for (Error_ cause : causes) {
                print(cause, depth + 1);
            }
        }
    }

    private static Node generate(Node root) {
        var list = Arrays.asList(
                new RootTypeRemover("package"),
                new RootTypeRemover("whitespace"),
                new MethodMigrator(),
                new ModifierAttacher(),
                new ClassSplitter(),
                new MagmaFormatter()
        );

        Node acc = root;
        for (Modifier modifier : list) {
            acc = modifier.generate(acc);
        }
        return acc;
    }
}
