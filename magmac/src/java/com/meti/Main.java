package com.meti;

import com.meti.node.MapNode;
import com.meti.render.MagmaRenderer;
import com.meti.rule.Rule;
import com.meti.rule.NodeSplitRule;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.meti.rule.DiscardRule.Discard;
import static com.meti.rule.ExtractRule.$;
import static com.meti.rule.NamingRule.Name;
import static com.meti.rule.NodeRule.Node;
import static com.meti.rule.RequireLeftRule.Left;
import static com.meti.rule.RequireRightRule.Right;
import static com.meti.rule.SplitByFirstSliceInclusiveRule.FirstInclusive;
import static com.meti.rule.SplitByFirstSliceRule.First;
import static com.meti.rule.SplitByLastSliceRule.Last;
import static com.meti.rule.StripRule.Strip;

public class Main {
    public static final Rule IMPORT_RULE = Name("import", Left("import ", Last($("parent"), ".", $("child"))));
    public static final Rule BLOCK_RULE = Strip(Left("{", Right($("lines"), "}")));
    public static final Rule CLASS_RULE = Name("class", First(Discard, "class ", FirstInclusive(Strip($("name")), "{",
            Node("content", Name("block", BLOCK_RULE)))));

    public static final Map<String, List<Rule>> RULES = Map.of("root", List.of(
            IMPORT_RULE,
            CLASS_RULE));

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
        return NodeSplitRule.split(input)
                .stream()
                .map(String::strip)
                .map(Main::compileRootElement)
                .flatMap(Optional::stream)
                .collect(Collectors.toList());
    }

    private static Optional<String> compileRootElement(String stripped) {
        if (stripped.isEmpty() || stripped.startsWith("package ")) return Optional.empty();
        return lex(stripped).map(MagmaRenderer::new).flatMap(MagmaRenderer::render);
    }

    private static Optional<MapNode> lex(String stripped) {
        return RULES.get("root")
                .stream()
                .map(rule -> rule.apply(stripped))
                .flatMap(Optional::stream)
                .findFirst()
                .flatMap(MapNode::fromTuple);
    }
}
