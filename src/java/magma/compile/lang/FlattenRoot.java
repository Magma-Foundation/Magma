package magma.compile.lang;

import jvm.collect.list.Lists;
import magma.collect.list.List_;
import magma.compile.CompileError;
import magma.compile.MapNode;
import magma.compile.Node;
import magma.compile.transform.State;
import magma.compile.transform.Transformer;
import magma.option.Tuple;
import magma.result.Ok;
import magma.result.Result;

public class FlattenRoot implements Transformer {
    private Node afterPass0(Node node) {
        if (!node.is("group")) return node;
        Node child = node.findNode("child").orElse(new MapNode());

        if (!child.is("root")) return node;
        List_<Node> oldChildren = child
                .findNode("content").orElse(new MapNode())
                .findNodeList("children").orElse(Lists.empty());

        List_<Node> newChildren = node.streamNodeLists()
                .foldWithInitial(Lists.empty(), (nodeList, tuple) -> nodeList.addAll(tuple.right()));

        return new MapNode("root").withNode("content", new MapNode("block").withNodeList("children", oldChildren.addAll(newChildren)));
    }

    private Result<Node, CompileError> afterPass0(State state, Node node) {
        return new Ok<>(afterPass0(node));
    }

    private Result<Node, CompileError> beforePass0(State state, Node node) {
        return new Ok<>(node);
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
