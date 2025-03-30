package magma.compile.lang;

import magma.collect.list.ListCollector;
import magma.collect.list.List_;
import magma.compile.Node;
import magma.option.Tuple;

public class TreeTransformingStage implements TransformingStage {
    private final Transformer transformer;

    public TreeTransformingStage(Transformer transformer) {
        this.transformer = transformer;
    }

    @Override
    public Node transform(Node tree, List_<String> namespace) {
        Node withNodes = tree.streamNodes()
                .foldWithInitial(tree, (node, tuple) -> mapNodes(namespace, node, tuple));

        Node withNodeLists = withNodes.streamNodeLists().foldWithInitial(withNodes,
                (node, tuple) -> mapNodeList(namespace, node, tuple));

        return transformer.afterPass(withNodeLists);
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