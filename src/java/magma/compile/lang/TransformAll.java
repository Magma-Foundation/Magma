package magma.compile.lang;

import jvm.collect.list.JavaList;
import jvm.collect.list.Lists;
import jvm.collect.stream.Streams;
import magma.collect.list.ListCollector;
import magma.collect.list.List_;
import magma.collect.stream.Joiner;
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

    @Override
    public Result<Node, CompileError> afterPass(State state, Node node) {
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
                int size = state.getNamespace().size();
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

        if (node.is("definition")) {
            Node type = node.findNode("type").orElse(new MapNode());
            if (type.is("generic")) {
                String value = type.findNodeList("base")
                        .orElse(Lists.empty())
                        .get(0)
                        .findString("value")
                        .orElse("");

                if (value.equals("Function")) {
                    List_<Node> arguments = type.findNodeList("arguments")
                            .orElse(Lists.empty());

                    Node param = arguments.get(0);
                    Node returns = arguments.get(1);

                    return new Ok<>(node.retype("functional-definition")
                            .removeNode("type")
                            .withNode("return", returns)
                            .withNodeList("params", Lists.of(param)));
                }
            }
        }

        if (node.is("symbol-type")) {
            String oldValue = node.findString("value").orElse("");
            if (oldValue.equals("boolean")) {
                return new Ok<>(node.withString("value", "int"));
            } else {
                return new Ok<>(node.retype("struct-type"));
            }
        }

        if (node.is("array")) {
            return new Ok<>(node.retype("ref"));
        }

        return new Ok<>(node);
    }

    private String stringify(Node node) {
        if (node.is("generic")) {
            String caller = node.findNodeList("base")
                    .orElse(Lists.empty())
                    .stream()
                    .map(element -> element.findString("value"))
                    .flatMap(Streams::fromOption)
                    .collect(new Joiner("_"))
                    .orElse("");

            return node.findNodeList("arguments").orElse(new JavaList<>())
                    .stream()
                    .map(this::stringify)
                    .collect(new Joiner("_"))
                    .map(arguments -> caller + "_" + arguments)
                    .orElse(caller);
        }

        return node.findString("value").orElse("?");
    }

    @Override
    public Result<Node, CompileError> beforePass(State state, Node node) {
        if (!node.is("root")) return new Ok<>(node);

        Node content = node.findNode("content").orElse(new MapNode());
        List_<Node> children = content.findNodeList("children").orElse(Lists.empty());

        List_<Node> newChildren = children.stream()
                .filter(child -> !isFunctionalImport(child) && !child.is("package") && !hasTypeParams(child))
                .collect(new ListCollector<>());

        Node withChildren = content.withNodeList("children", newChildren);
        return new Ok<>(node.withNode("content", withChildren));
    }
}