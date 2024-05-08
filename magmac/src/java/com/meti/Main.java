package com.meti;

import com.meti.render.MagmaRenderer;
import com.meti.rule.NamingRule;
import com.meti.rule.Rule;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.meti.rule.DiscardRule.Discard;
import static com.meti.rule.ExtractRule.$;
import static com.meti.rule.RequireLeftRule.Left;
import static com.meti.rule.RequireRightRule.Right;
import static com.meti.rule.SplitByFirstSliceRule.First;
import static com.meti.rule.SplitByLastSliceRule.Last;
import static com.meti.rule.StripRule.Strip;

public class Main {
    public static final Rule IMPORT_RULE = new NamingRule("import", Left("import ", Last($("parent"), ".", $("child"))));
    public static final Rule CLASS_RULE = new NamingRule("class", First(Discard, "class ",
            First(Strip($("name")), "{", Right($("content"), "}"))));

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
        return split(input).stream().map(String::strip).map(Main::compileRootElement).flatMap(Optional::stream).collect(Collectors.toList());
    }

    private static Optional<String> compileRootElement(String stripped) {
        if (stripped.isEmpty() || stripped.startsWith("package ")) return Optional.empty();
        var value = Stream.<Supplier<Optional<String>>>of(() -> compileImport(stripped), () -> compileClass(stripped)).map(Supplier::get).flatMap(Optional::stream).findFirst().orElse(stripped);
        return Optional.of(value);
    }

    private static Optional<String> compileClass(String stripped) {
        return CLASS_RULE.apply(stripped)
                .flatMap(MapNode::fromTuple)
                .map(MagmaRenderer::new)
                .flatMap(MagmaRenderer::render);
    }

    private static Optional<String> compileImport(String stripped) {
        return IMPORT_RULE.apply(stripped)
                .flatMap(MapNode::fromTuple)
                .map(MagmaRenderer::new)
                .flatMap(MagmaRenderer::render);
    }

    private static ArrayList<String> split(String input) {
        var current = new SplitState();
        for (int i = 0; i < input.length(); i++) {
            current = processChar(input.charAt(i), current);
        }

        return current.advance().lines;
    }

    private static SplitState processChar(char c, SplitState current) {
        if (c == ';' && current.depth == 0) return current.advance();
        return switch (c) {
            case '{' -> current.enter().append(c);
            case '}' -> current.exit().append(c);
            default -> current.append(c);
        };
    }

    private record SplitState(int depth, ArrayList<String> lines, StringBuilder builder) {
        public SplitState() {
            this(0, new ArrayList<>(), new StringBuilder());
        }

        private SplitState append(char c) {
            return new SplitState(depth, lines, this.builder.append(c));
        }

        private SplitState exit() {
            return new SplitState(depth - 1, lines, builder);
        }

        private SplitState enter() {
            return new SplitState(depth + 1, lines, builder);
        }

        private SplitState advance() {
            var copy = new ArrayList<>(lines);
            copy.add(builder.toString());
            return new SplitState(depth, copy, new StringBuilder());
        }
    }
}
