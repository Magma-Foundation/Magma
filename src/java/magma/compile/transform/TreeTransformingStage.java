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
    public Result<Node, CompileError> transform(Node root, State state) {
        return root.streamNodes()
                .foldToResult(root, (node, tuple) -> mapNodes(node, tuple, state))
                .flatMapValue(withNodes -> transformNodeLists(withNodes, state));
    }

    private Result<Node, CompileError> transformNodeLists(Node withNodes, State state) {
        return withNodes.streamNodeLists()
                .foldToResult(withNodes, (node, tuple) -> mapNodeList(node, tuple, state))
                .flatMapValue(node1 -> transformer.afterPass(state, node1));
    }

    private Result<Node, CompileError> mapNodes(Node node, Tuple<String, Node> tuple, State state) {
        return transform(tuple.right(), state).mapValue(newChild -> node.withNode(tuple.left(), newChild));
    }

    private Result<Node, CompileError> mapNodeList(Node node, Tuple<String, List_<Node>> tuple, State state) {
        return tuple.right()
                .stream()
                .foldToResult(Lists.<Node>empty(), (current, element) -> mapNodeListElement(current, element, state))
                .mapValue(children -> node.withNodeList(tuple.left(), children));
    }

    private Result<List_<Node>, CompileError> mapNodeListElement(List_<Node> elements, Node element, State state) {
        return transform(element, state).mapValue(elements::add);
    }
}