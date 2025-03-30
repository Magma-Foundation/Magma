package magma.compile.lang;

import jvm.collect.list.Lists;
import jvm.collect.stream.Streams;
import magma.collect.list.ListCollector;
import magma.collect.list.List_;
import magma.compile.MapNode;
import magma.compile.Node;
import magma.option.Tuple;

public class JavaCTransformer implements Transformer {
    private static Node afterPass(Node node) {
        if (node.is("root")) {
            List_<Node> newChildren = node.findNodeList("children")
                    .orElse(Lists.empty())
                    .stream()
                    .flatMap(child -> {
                        if (child.is("package")) {
                            return Streams.empty();
                        } else {
                            return Streams.of(child);
                        }
                    })
                    .collect(new ListCollector<>());

            return node.withNodeList("children", newChildren);
        }

        if (node.is("interface")) {
            Tuple<List_<Node>, List_<Node>> children = node.findNodeList("children")
                    .orElse(Lists.empty())
                    .stream()
                    .foldWithInitial(new Tuple<>(Lists.empty(), Lists.empty()), JavaCTransformer::bucketClassMember);

            Node withChildren = node.retype("struct").withNodeList("children", children.left());
            return new MapNode("group")
                    .withNode("child", withChildren)
                    .withNodeList("functions", children.right());
        }

        if (node.is("method")) {
            return node.retype("function");
        }

        return node;
    }

    private static Tuple<List_<Node>, List_<Node>> bucketClassMember(Tuple<List_<Node>, List_<Node>> tuple, Node element) {
        List_<Node> definitions = tuple.left();
        List_<Node> others = tuple.right();

        if (element.is("definition")) {
            return new Tuple<>(definitions.add(element), others);
        } else {
            return new Tuple<>(definitions, others.add(element));
        }
    }

    @Override
    public Node transform(Node tree, List_<String> namespace) {
        Node withNodes = tree.streamNodes()
                .foldWithInitial(tree, (node, tuple) -> mapNodes(namespace, node, tuple));

        Node withNodeLists = withNodes.streamNodeLists().foldWithInitial(withNodes,
                (node, tuple) -> mapNodeList(namespace, node, tuple));

        return afterPass(withNodeLists);
    }

    private Node mapNodes(List_<String> namespace, Node node, Tuple<String, Node> tuple) {
        Node newChild = transform(tuple.right(), namespace);
        return node.withNode(tuple.left(), newChild);
    }

    private Node mapNodeList(List_<String> namespace, Node node, Tuple<String, List_<Node>> tuple) {
        List_<Node> children = tuple.right()
                .stream()
                .map(child -> transform(child, namespace))
                .collect(new ListCollector<>());

        return node.withNodeList(tuple.left(), children);
    }
}