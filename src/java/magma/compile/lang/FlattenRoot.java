package magma.compile.lang;

import jvm.collect.list.Lists;
import magma.collect.list.List_;
import magma.compile.MapNode;
import magma.compile.Node;
import magma.compile.transform.Transformer;

public class FlattenRoot implements Transformer {
    @Override
    public Node afterPass(Node node) {
        if (!node.is("group")) return node;
        Node child = node.findNode("child").orElse(new MapNode());

        if (!child.is("root")) return node;
        List_<Node> oldChildren = node.findNodeList("children").orElse(Lists.empty());

        List_<Node> newChildren = node.streamNodeLists()
                .foldWithInitial(Lists.empty(), (nodeList, tuple) -> nodeList.addAll(tuple.right()));

        return new MapNode("root").withNodeList("children", oldChildren.addAll(newChildren));
    }
}
