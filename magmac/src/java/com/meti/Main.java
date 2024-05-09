package com.meti;

import com.meti.lang.JavaLang;
import com.meti.node.MapNode;
import com.meti.render.MagmaRenderer;
import com.meti.rule.TypeRule;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.meti.rule.ExtractRule.$;
import static com.meti.rule.NodeSplitRule.split;
import static com.meti.rule.RequireLeftRule.Left;
import static com.meti.rule.RequireRightRule.Right;
import static com.meti.rule.SplitByLastSliceRule.Last;
import static com.meti.rule.TypeRule.Type;

public class Main {
    public static final TypeRule IMPORT;

    static {
        var last = Last(Left("{ ", Right($("child"), " }")), " from ", $("parent"));
        IMPORT = Type("import", Left("import ", Right(last, ";\n")));
    }

    public static void main(String[] args) {
        var source = Paths.get(".", "magmac", "src", "java", "com", "meti", "Main.java");
        try {
            var input = Files.readString(source);
            var target = source.resolveSibling("Main.mgs");
            var outputContent = compile(input);
            var output = String.join("", outputContent);
            Files.writeString(target, output);
        } catch (IOException e) {
            throw new RuntimeException(source.toAbsolutePath().toString(), e);
        }
    }

    private static List<String> compile(String input) {
        return split(input)
                .stream()
                .map(String::strip)
                .map(Main::compileRootElement)
                .flatMap(Optional::stream)
                .collect(Collectors.toList());
    }

    private static Optional<String> compileRootElement(String stripped) {
        if (stripped.isEmpty() || stripped.startsWith("package ")) return Optional.empty();
        return lex(stripped).flatMap(node -> renderImpl(node));
    }

    private static Optional<String> renderImpl(MapNode node) {
        return IMPORT.toString(node).or(() -> getRender(node));
    }

    private static Optional<String> getRender(MapNode node) {
        return new MagmaRenderer(node).render();
    }

    private static Optional<MapNode> lex(String stripped) {
        return JavaLang.ROOT_RULES.get("root")
                .stream()
                .map(rule -> rule.fromString(stripped))
                .flatMap(Optional::stream)
                .findFirst()
                .flatMap(MapNode::fromTuple);
    }
}
