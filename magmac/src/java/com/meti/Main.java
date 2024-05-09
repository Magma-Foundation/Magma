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
        return new Some<>(transformAST(child));
    }

    private static MapNode transformAST(MapNode child) {
        var transformed = transformPreVisit(child).orElse(child);
        return transformed.map(NodeFactory, Main::visitChild).map(NodeListFactory, Main::visitChildren);
    }

    private static Option<MapNode> transformPreVisit(MapNode child) {
        if(child.is("method")) {
            return new Some<>(child.with("indent", new StringAttribute("\t")));
        }

        if (child.is("declaration")) {
            return new Some<>(child.with("indent", new StringAttribute("\t\t")));
        }
        return new None<>();
    }

    private static Option<List<MapNode>> visitChildren(List<MapNode> mapNodes) {
        return new Some<>(mapNodes.stream()
                .map(Main::transformAST)
                .collect(Collectors.toList()));
    }

    private static Option<MapNode> visitChild(MapNode node) {
        return new Some<>(transformAST(node));
    }
}
