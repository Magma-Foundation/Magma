package magma.compile.lang;

import jvm.collect.list.Lists;
import magma.collect.list.List_;
import magma.compile.CompileError;
import magma.compile.MapNode;
import magma.compile.Node;
import magma.compile.transform.Transformer;
import magma.result.Ok;
import magma.result.Result;

public class FlattenRoot implements Transformer {
    private Node afterPass0(Node node) {
        if (!node.is("group")) return node;
        Node child = node.findNode("child").orElse(new MapNode());

        if (!child.is("root")) return node;
        List_<Node> oldChildren = node
                .findNode("content").orElse(new MapNode())
                .findNodeList("children").orElse(Lists.empty());

        List_<Node> newChildren = node.streamNodeLists()
                .foldWithInitial(Lists.empty(), (nodeList, tuple) -> nodeList.addAll(tuple.right()));

        return new MapNode("root").withNode("content", new MapNode("block").withNodeList("children", oldChildren.addAll(newChildren)));
    }

    @Override
    public Result<Node, CompileError> afterPass(Node node) {
        return new Ok<>(afterPass0(node));
    }
}
