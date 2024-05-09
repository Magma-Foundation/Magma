package com.meti;

import com.meti.node.*;
import com.meti.util.None;
import com.meti.util.Option;
import com.meti.util.Some;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static com.meti.lang.JavaLang.JAVA_ROOT;
import static com.meti.lang.MagmaLang.MAGMA_ROOT;
import static com.meti.node.NodeListAttribute.NodeListFactory;
import static com.meti.util.Options.*;

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
        var inputAST = JAVA_ROOT.fromString(input)
                .map(Tuple::left)
                .flatMap(tuple -> toNative(tuple.apply("roots")))
                .flatMap(attribute -> toNative(attribute.asListOfNodes()))
                .orElse(Collections.emptyList());

        var outputAST = new ArrayList<MapNode>();
        for (MapNode child : inputAST) {
            var outputChild = transform(child).orElse(child);
            outputAST.add(outputChild);
        }

        return outputAST.stream()
                .map(MAGMA_ROOT::toString)
                .flatMap(Optional::stream)
                .collect(Collectors.toList());
    }

    private static Option<MapNode> transform(MapNode child) {
        return $Option(() -> {
            if (!child.is("class")) return $$();

            var content = child.apply("content").$().asNode().$();
            var newContent = content.map("children", NodeListFactory, inputContent -> {
                var outputContent = new ArrayList<MapNode>();
                for (var input : inputContent) {
                    outputContent.add(attachIndent(input).orElse(input));
                }

                return outputContent;
            }).$();

            return child.with("content", new NodeAttribute(newContent));
        });
    }

    private static Option<MapNode> attachIndent(MapNode input) {
        if (input.is("method")) {
            return new Some<>(input.with("indent", new StringAttribute("\t")));
        } else {
            return new None<>();
        }
    }
}
