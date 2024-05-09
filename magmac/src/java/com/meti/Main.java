package com.meti;

import com.meti.node.MapNode;
import com.meti.node.StringAttribute;
import com.meti.util.None;
import com.meti.util.Option;
import com.meti.util.Some;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.meti.lang.JavaLang.JAVA_ROOT;
import static com.meti.lang.MagmaLang.DECLARATION;
import static com.meti.lang.MagmaLang.MAGMA_ROOT;
import static com.meti.node.NodeAttribute.NodeFactory;
import static com.meti.node.NodeListAttribute.NodeListFactory;
import static com.meti.util.Options.toNative;

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
        if (!child.is("class")) return new None<>();

        return child.map("content", NodeFactory, Main::parseContentChildren);
    }

    private static Option<MapNode> parseContentChildren(MapNode content) {
        return content.map("children", NodeListFactory, Main::parseContentChild);
    }

    private static Some<List<MapNode>> parseContentChild(List<MapNode> inputContent) {
        var outputContent = new ArrayList<MapNode>();
        for (var input : inputContent) {
            outputContent.add(attachIndent(input).orElse(input));
        }

        return new Some<>(outputContent);
    }

    private static Option<MapNode> attachIndent(MapNode previous) {
        if (!previous.is("method")) return new None<>();

        return previous.with("indent", new StringAttribute("\t")).map("children", NodeListFactory, children -> {
            return new Some<>(children.stream()
                    .map(child -> child.with("indent", new StringAttribute("\t\t")))
                    .collect(Collectors.toList()));
        });
    }
}
