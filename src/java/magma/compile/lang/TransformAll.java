package magma.compile.lang;

import jvm.collect.list.Lists;
import jvm.collect.stream.Streams;
import magma.collect.list.ListCollector;
import magma.collect.list.List_;
import magma.compile.CompileError;
import magma.compile.MapNode;
import magma.compile.Node;
import magma.compile.context.NodeContext;
import magma.compile.transform.State;
import magma.compile.transform.Transformer;
import magma.option.Tuple;
import magma.result.Err;
import magma.result.Ok;
import magma.result.Result;

public class TransformAll implements Transformer {
    private int counter = 0;

    static Tuple<List_<Node>, List_<Node>> bucketClassMember(Tuple<List_<Node>, List_<Node>> tuple, Node element) {
        List_<Node> definitions = tuple.left();
        List_<Node> others = tuple.right();

        if (element.is("definition")) return new Tuple<>(definitions.add(element), others);
        if (element.is("initialization")) {
            Node definition = element.findNode("definition").orElse(new MapNode());
            return new Tuple<>(definitions.add(definition), others);
        }

        return new Tuple<>(definitions, others.add(element));
    }

    private static Result<Node, CompileError> find(Node node, String propertyKey) {
        return node.findNode(propertyKey)
                .<Result<Node, CompileError>>map(Ok::new)
                .orElseGet(() -> new Err<>(new CompileError("Node '" + propertyKey + "' not present", new NodeContext(node))));
    }

    private static Result<List_<Node>, CompileError> findNodeList(Node value, String propertyKey) {
        return value.findNodeList(propertyKey)
                .<Result<List_<Node>, CompileError>>map(Ok::new)
                .orElseGet(() -> new Err<>(new CompileError("Node list '" + propertyKey + "' not present", new NodeContext(value))));
    }

    private static boolean isFunctionalImport(Node child) {
        if (!child.is("import")) return false;

        List_<String> namespace = child.findNodeList("namespace")
                .orElse(Lists.empty())
                .stream()
                .map(segment -> segment.findString("value"))
                .flatMap(Streams::fromOption)
                .collect(new ListCollector<>());

        return namespace.size() >= 3 && namespace.subList(0, 3).equalsTo(Lists.of("java", "util", "function"));
    }

    private static boolean hasTypeParams(Node child) {
        List_<Node> typeParams = child.findNodeList("type-params").orElse(Lists.empty());
        return !typeParams.isEmpty();
    }

    private Result<Node, CompileError> afterPass0(State state, Node node) {
        if (node.is("interface") || node.is("record") || node.is("class")) {
            return find(node, "content").flatMapValue(value -> {
                return findNodeList(value, "children").mapValue(children -> {
                    Tuple<List_<Node>, List_<Node>> newChildren = children.stream()
                            .foldWithInitial(new Tuple<>(Lists.empty(), Lists.empty()), TransformAll::bucketClassMember);

                    Node withChildren = node.retype("struct").withNode("content", new MapNode("block")
                            .withNodeList("children", newChildren.left()));

                    return new MapNode("group")
                            .withNode("child", withChildren)
                            .withNodeList("functions", newChildren.right());
                });
            });
        }

        if (node.is("method")) {
            return new Ok<>(node.retype("function"));
        }

        if (node.is("block")) {
            return new Ok<>(node.mapNodeList("children", children -> {
                return children.stream()
                        .filter(child -> !child.is("whitespace"))
                        .collect(new ListCollector<>());
            }));
        }

        if (node.is("import")) {
            return findNodeList(node, "namespace").mapValue(requestedNodes -> {
                List_<String> requestedNamespace = requestedNodes.stream()
                        .map(child -> child.findString("value"))
                        .flatMap(Streams::fromOption)
                        .collect(new ListCollector<>());

                List_<String> outputNamespace = Lists.empty();
                int size = state.namespace().size();
                if (size == 0) {
                    outputNamespace = outputNamespace.add(".");
                } else {
                    for (int i = 0; i < size; i++) {
                        outputNamespace = outputNamespace.add("..");
                    }
                }

                List_<String> newNamespace = requestedNamespace.popFirst()
                        .map(first -> first.left().equals("jvm") ? Lists.of("windows").addAll(first.right()) : requestedNamespace)
                        .orElse(requestedNamespace);

                outputNamespace = outputNamespace.addAll(newNamespace);

                List_<Node> path = outputNamespace.stream()
                        .map(segment -> new MapNode().withString("value", segment))
                        .collect(new ListCollector<>());

                return node.retype("include").withNodeList("path", path);
            });
        }

        if (node.is("symbol-type")) {
            String oldValue = node.findString("value").orElse("");
            if (oldValue.equals("boolean")) return new Ok<>(node.withString("value", "int"));
            if (oldValue.equals("int")) return new Ok<>(node);
            return new Ok<>(node.retype("struct-type"));
        }

        if (node.is("array")) {
            return new Ok<>(node.retype("ref"));
        }

        if (node.is("lambda")) {
            Node child = node.findNode("child")
                    .orElse(new MapNode());

            String generatedName = "__lambda" + counter + "__";
            counter++;

            Node definition = new MapNode("definition")
                    .withString("name", generatedName)
                    .withNode("type", new MapNode("symbol-type").withString("value", "auto"));

            Node function = new MapNode("function")
                    .withNode("definition", definition);

            return new Ok<>(new MapNode("group")
                    .withNode("child", new MapNode("symbol-value").withString("value", generatedName))
                    .withNodeList("functions", Lists.of(function)));
        }

        if (node.is("method-access")) {
            return new Ok<>(node.retype("data-access"));
        }

        return new Ok<>(node);
    }

    private Result<Node, CompileError> beforePass0(State state, Node node) {
        if (!node.is("root")) return new Ok<>(node);

        Node content = node.findNode("content").orElse(new MapNode());
        List_<Node> children = content.findNodeList("children").orElse(Lists.empty());

        List_<Node> newChildren = children.stream()
                .filter(child -> !isFunctionalImport(child) && !child.is("package"))
                .collect(new ListCollector<>());

        Node withChildren = content.withNodeList("children", newChildren);
        return new Ok<>(node.withNode("content", withChildren));
    }

    @Override
    public Result<Tuple<State, Node>, CompileError> beforePass(State state, Node node) {
        return beforePass0(state, node).mapValue(value -> new Tuple<>(state, value));
    }

    @Override
    public Result<Tuple<State, Node>, CompileError> afterPass(State state, Node node) {
        return afterPass0(state, node).mapValue(value -> new Tuple<>(state, value));
    }
}