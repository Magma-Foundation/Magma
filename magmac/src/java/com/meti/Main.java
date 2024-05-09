package com.meti;

import com.meti.node.MapNode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.meti.lang.JavaLang.JAVA_ROOT;
import static com.meti.lang.MagmaLang.MAGMA_ROOT;
import static com.meti.rule.NodeSplitRule.split;

public class Main {

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
        return lex(stripped).flatMap(MAGMA_ROOT::toString);
    }

    private static Optional<MapNode> lex(String stripped) {
        return JAVA_ROOT.get("root")
                .stream()
                .map(rule -> rule.fromString(stripped))
                .flatMap(Optional::stream)
                .findFirst()
                .flatMap(MapNode::fromTuple);
    }
}
