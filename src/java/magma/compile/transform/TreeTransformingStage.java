package magma.compile.transform;

import jvm.collect.list.Lists;
import magma.collect.list.List_;
import magma.compile.CompileError;
import magma.compile.Node;
import magma.option.Tuple;
import magma.result.Result;

public class TreeTransformingStage implements TransformingStage {
    private final Transformer transformer;

    public TreeTransformingStage(Transformer transformer) {
        this.transformer = transformer;
    }

    @Override
    public Result<Node, CompileError> transform(Node tree, State state) {
        return tree.streamNodes()
                .foldToResult(tree, (node, tuple) -> mapNodes(state.namespace(), node, tuple))
                .flatMapValue(withNodes -> transformNodeLists(state.namespace(), withNodes));
    }

    private Result<Node, CompileError> transformNodeLists(List_<String> namespace, Node withNodes) {
        return withNodes.streamNodeLists()
                .foldToResult(withNodes, (node, tuple) -> mapNodeList(namespace, node, tuple))
                .flatMapValue(node1 -> transformer.afterPass(new State(namespace), node1));
    }

    private Result<Node, CompileError> mapNodes(List_<String> namespace, Node node, Tuple<String, Node> tuple) {
        return transform(tuple.right(), new State(namespace)).mapValue(newChild -> node.withNode(tuple.left(), newChild));
    }

    private Result<Node, CompileError> mapNodeList(List_<String> namespace, Node node, Tuple<String, List_<Node>> tuple) {
        return tuple.right()
                .stream()
                .foldToResult(Lists.<Node>empty(), (current, element) -> mapNodeListElement(namespace, current, element))
                .mapValue(children -> node.withNodeList(tuple.left(), children));
    }

    private Result<List_<Node>, CompileError> mapNodeListElement(List_<String> namespace, List_<Node> elements, Node element) {
        return transform(element, new State(namespace)).mapValue(elements::add);
    }
}