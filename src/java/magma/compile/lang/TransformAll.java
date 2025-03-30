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

    @Override
    public Result<Node, CompileError> afterPass(Node node) {
        if (node.is("root")) {
            return find(node, "content").flatMapValue(value -> {
                return findNodeList(value, "children").mapValue(children -> {
                    List_<Node> newChildren = children.stream()
                            .flatMap(child -> child.is("package") ? Streams.empty() : Streams.of(child))
                            .collect(new ListCollector<>());

                    return node.withNode("content", new MapNode("block").withNodeList("children", newChildren));
                });
            });
        }

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
            return findNodeList(node, "namespace").mapValue(namespace -> {
                return node.retype("include").withNodeList("path", namespace);
            });
        }

        if (node.is("generic")) {
            String stringify = stringify(node);
            return new Ok<>(new MapNode("symbol-type").withString("value", stringify));
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

            String arguments = node.findNodeList("arguments").orElse(new JavaList<>())
                    .stream()
                    .map(this::stringify)
                    .collect(new Joiner("_"))
                    .orElse("");

            return caller + "_" + arguments;
        }

        return node.findString("value").orElse("");
    }
}