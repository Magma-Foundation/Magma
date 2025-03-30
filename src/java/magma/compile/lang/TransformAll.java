package magma.compile.lang;

import jvm.collect.list.Lists;
import jvm.collect.stream.Streams;
import magma.collect.list.ListCollector;
import magma.collect.list.List_;
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

        if (element.is("definition")) {
            return new Tuple<>(definitions.add(element), others);
        } else {
            return new Tuple<>(definitions, others.add(element));
        }
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

        if (node.is("interface")) {
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

        return new Ok<>(node);
    }
}