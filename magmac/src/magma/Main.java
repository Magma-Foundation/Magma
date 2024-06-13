package magma;

import magma.api.Results;
import magma.compile.CompileException;
import magma.compile.lang.JavaLang;
import magma.compile.lang.JavaToMagmaGenerator;
import magma.compile.lang.MagmaLang;
import magma.compile.rule.Rule;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static final String CLASS_KEYWORD_WITH_SPACE = "class ";

    public static void main(String[] args) {
        try {
            var source = Paths.get(".", "magmac", "src", "magma", "Main.java");
            var input = Files.readString(source);
            var target = source.resolveSibling("Main.mgs");
            var root = JavaLang.createRootRule().toNode(input).create().orElseThrow();
            var generated = new JavaToMagmaGenerator().generate(root);
            Rule rule = MagmaLang.createRootRule();
            Files.writeString(target, Results.unwrap(rule.fromNode(generated)));
        } catch (IOException | CompileException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }
}
