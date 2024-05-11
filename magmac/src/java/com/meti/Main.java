package com.meti;

import com.meti.node.MapNode;
import com.meti.node.StringAttribute;
import com.meti.util.None;
import com.meti.util.Option;
import com.meti.util.Some;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.meti.lang.JavaLang.JAVA_ROOT;
import static com.meti.lang.MagmaLang.MAGMA_ROOT;
import static com.meti.node.NodeAttribute.NodeFactory;
import static com.meti.node.NodeListAttribute.NodeListFactory;
import static com.meti.util.Options.toNative;

public class Main {
    public static void main(String[] args) {
        var sourceDirectory = Paths.get(".", "magmac", "src", "java");
        var targetDirectory = Paths.get(".", "magmac", "build");

        try (var stream = Files.walk(sourceDirectory)) {
            var sources = stream.filter(Files::isRegularFile)
                    .map(file -> new PathSource(sourceDirectory, file))
                    .toList();

            compileSources(sources, targetDirectory);
        } catch (IOException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }

    private static void compileSources(List<PathSource> sources, Path targetDirectory) throws IOException {
        for (PathSource pathSource : sources) {
            compileSource(targetDirectory, pathSource);
        }
    }

    private static void compileSource(Path targetDirectory, PathSource pathSource) throws IOException {
        var namespace = pathSource.computeNamespace();
        var name = pathSource.computeName();

        try {
            var input = pathSource.read();

            var targetParent = namespace.stream().reduce(targetDirectory, Path::resolve, (previous, next) -> next);
            if (!Files.exists(targetParent)) Files.createDirectories(targetParent);

            var target = targetParent.resolve(name + ".mgs");

            var outputContent = compile(input);
            var output = String.join("", outputContent);
            Files.writeString(target, output);
        } catch (RuntimeException e) {
            System.out.printf("%s.%s: Unknown token: %s%n", String.join(".", namespace), name, e.getMessage());

            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }

    private static List<String> compile(String input) {
        var rootResult = JAVA_ROOT.fromString(input);
        var attributes = rootResult.getAttributes();
        if (attributes.isEmpty()) {
            throw rootResult.toException();
        }

        var inputAST = attributes
                .flatMap(tuple -> toNative(tuple.apply("roots")))
                .flatMap(attribute -> toNative(attribute.asListOfNodes()))
                .orElse(Collections.emptyList());

        var outputAST = visitChildren(inputAST, new State())
                .flatMap(Tuple::left)
                .orElse(Collections.emptyList());

        var builder = new ArrayList<String>();
        for (MapNode mapNode : outputAST) {
            var rendered = MAGMA_ROOT.toString(mapNode);
            if (rendered.isPresent()) {
                builder.add(rendered.get());
            } else {
                throw new UnsupportedOperationException("Cannot render: " + mapNode);
            }
        }

        return builder;
    }

    private static Tuple<Option<MapNode>, State> transformAST(MapNode child, State state) {
        var preVisited = transformPreVisit(child, state).orElse(new Tuple<>(new Some<>(child), state));
        var withNodes = preVisited.left()
                .map(node1 -> node1.map(NodeFactory, (node, state1) -> visitChild(state1, node), preVisited.right()))
                .orElse(preVisited);

        var withNodeLists = withNodes.left()
                .map(inner -> {
                    return inner.map(NodeListFactory, (node, state1) -> visitChildren(state1, node), withNodes.right());
                })
                .orElse(withNodes);

        return withNodeLists.left().flatMap(left -> {
            return transformPostVisit(left, withNodeLists.right());
        }).orElse(withNodeLists);
    }

    private static Option<Tuple<Option<MapNode>, State>> transformPostVisit(MapNode child, State state) {
        if (child.is("block")) {
            var exited = state.exit();
            return new Some<>(new Tuple<>(new Some<>(attachIndent(child, exited)), exited));
        }

/*        if (child.is("record")) {
            var mapNode = new MapNode("block", new NodeAttributes(Map.of("children", new NodeListAttribute(Collections.emptyList()))));

            var function = child.rename("function")
                    .with("indent", new StringAttribute(""))
                    .with("value", new NodeAttribute(mapNode));

            return new Some<>(new Tuple<>(new Some<>(function), state));
        }*/

        return new None<>();
    }

    private static MapNode attachIndent(MapNode child, State state) {
        return child.with("indent", new StringAttribute("\t".repeat(state.depth)));
    }

    private static Option<Tuple<Option<MapNode>, State>> transformPreVisit(MapNode child, State state) {
        if (child.is("block")) {
            return new Some<>(new Tuple<>(new Some<>(child), state.enter()));
        }

        if (child.is("catch") || child.is("declaration") || child.is("method") || child.is("try")) {
            return new Some<>(new Tuple<>(new Some<>(attachIndent(child, state)), state));
        }

        if (child.is("package")) {
            return new Some<>(new Tuple<>(new None<>(), state));
        }

        return new None<>();
    }

    private static Option<Tuple<Option<List<MapNode>>, State>> visitChildren(List<MapNode> mapNodes, State state) {
        var list = new ArrayList<MapNode>();
        var current = state;

        for (MapNode child : mapNodes) {
            var tuple = transformAST(child, state);
            tuple.left().ifPresent(list::add);
            current = tuple.right();
        }

        return new Some<>(new Tuple<>(new Some<>(list), current));
    }

    private static Option<Tuple<Option<MapNode>, State>> visitChild(MapNode node, State state) {
        return new Some<>(transformAST(node, state));
    }

    record PathSource(Path root, Path file) {
        List<String> computeNamespace() {
            var paths = root.relativize(file);
            var list = new ArrayList<String>();
            for (int i = 0; i < paths.getNameCount() - 1; i++) {
                list.add(paths.getName(i).toString());
            }

            return list;
        }

        String computeName() {
            var fileName = file.getFileName().toString();
            var separator = fileName.indexOf('.');
            return fileName.substring(0, separator);
        }

        String read() throws IOException {
            return Files.readString(file);
        }
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
