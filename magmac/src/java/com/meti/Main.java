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

        var outputAST = visitChildren(inputAST, new State())
                .map(Tuple::left)
                .orElse(Collections.emptyList());

        return outputAST.stream()
                .map(MAGMA_ROOT::toString)
                .flatMap(Optional::stream)
                .collect(Collectors.toList());
    }

    private static Tuple<MapNode, State> transformAST(MapNode child, State state) {
        var preVisited = transformPreVisit(child, state).orElse(new Tuple<>(child, state));
        var withNodes = preVisited.left().map(NodeFactory, (node, state1) -> visitChild(state1, node), preVisited.right());
        var withNodeLists = withNodes.left().map(NodeListFactory, (node, state1) -> visitChildren(state1, node), withNodes.right());
        return transformPostVisit(withNodeLists.left(), withNodeLists.right()).orElse(withNodeLists);
    }

    private static Option<Tuple<MapNode, State>> transformPostVisit(MapNode child, State state) {
        if (child.is("block")) {
            var exited = state.exit();
            return new Some<>(new Tuple<>(attachIndent(child, exited), exited));
        }

        return new None<>();
    }

    private static MapNode attachIndent(MapNode child, State state) {
        return child.with("indent", new StringAttribute("\t".repeat(state.depth)));
    }

    private static Option<Tuple<MapNode, State>> transformPreVisit(MapNode child, State state) {
        if (child.is("block")) {
            return new Some<>(new Tuple<>(child, state.enter()));
        }

        if (child.is("method")) {
            return new Some<>(new Tuple<>(attachIndent(child, state), state));
        }

        if (child.is("declaration")) {
            return new Some<>(new Tuple<>(attachIndent(child, state), state));
        }

        return new None<>();
    }

    private static Option<Tuple<List<MapNode>, State>> visitChildren(List<MapNode> mapNodes, State state) {
        var list = new ArrayList<MapNode>();
        var current = state;

        for (MapNode child : mapNodes) {
            var tuple = transformAST(child, state);
            list.add(tuple.left());
            current = tuple.right();
        }

        return new Some<>(new Tuple<>(list, current));
    }

    private static Option<Tuple<MapNode, State>> visitChild(MapNode node, State state) {
        return new Some<>(transformAST(node, state));
    }

    private record State(int depth) {
        public State() {
            this(0);
        }

        public State enter() {
            return new State(depth + 1);
        }

        public State exit() {
            return new State(depth - 1);
        }
    }
}
