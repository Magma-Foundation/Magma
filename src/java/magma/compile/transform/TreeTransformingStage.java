package magma.compile.transform;

import jvm.collect.list.Lists;
import magma.collect.list.List_;
import magma.compile.CompileError;
import magma.compile.Node;
import magma.compile.context.NodeContext;
import magma.option.Tuple;
import magma.result.Result;

public class TreeTransformingStage implements TransformingStage {
    private final Transformer transformer;

    public TreeTransformingStage(Transformer transformer) {
        this.transformer = transformer;
    }

    private static Tuple<State, Node> attachChildren(Node node, String propertyKey, Tuple<State, List_<Node>> children) {
        State newState = children.left();
        List_<Node> newChildren = children.right();
        Node withChildren = node.withNodeList(propertyKey, newChildren);
        return new Tuple<>(newState, withChildren);
    }

    private Result<Tuple<State, Node>, CompileError> transformNodes(State state, Node root) {
        return root.streamNodes()
                .foldToResult(new Tuple<>(state, root), (current, entry) -> transformNodes(current.left(), current.right(), entry))
                .flatMapValue(withNodes -> transformNodeLists(withNodes.left(), withNodes.right()));
    }

    private Result<Tuple<State, Node>, CompileError> transformNodeLists(State state, Node root) {
        return root.streamNodeLists()
                .foldToResult(new Tuple<>(state, root), (current, tuple) -> transformNodeList(current.left(), current.right(), tuple))
                .flatMapValue(withNodeLists -> transformer.afterPass(withNodeLists.left(), withNodeLists.right()));
    }

    private Result<Tuple<State, Node>, CompileError> transformNodes(State state, Node node, Tuple<String, Node> entry) {
        return transform(state, entry.right()).mapValue(newChild -> {
            Node withChild = node.withNode(entry.left(), newChild.right());
            return new Tuple<>(newChild.left(), withChild);
        });
    }

    private Result<Tuple<State, Node>, CompileError> transformNodeList(State state, Node node, Tuple<String, List_<Node>> entry) {
        String propertyKey = entry.left();
        List_<Node> propertyValues = entry.right();
        return propertyValues.stream()
                .foldToResult(new Tuple<>(state, Lists.empty()), this::transformElement)
                .mapValue(children -> attachChildren(node, propertyKey, children));
    }

    private Result<Tuple<State, List_<Node>>, CompileError> transformElement(
            Tuple<State, List_<Node>> current,
            Node element
    ) {
        State currentState = current.left();
        List_<Node> currentChildren = current.right();

        return transform(currentState, element).mapValue((Tuple<State, Node> newTuple) -> {
            State newState = newTuple.left();
            Node newChild = newTuple.right();
            return new Tuple<>(newState, currentChildren.add(newChild));
        });
    }

    @Override
    public Result<Tuple<State, Node>, CompileError> transform(State state, Node root) {
        return transformer.beforePass(state, root)
                .flatMapValue(beforePass -> transformNodes(beforePass.left(), beforePass.right()))
                .mapErr(err -> new CompileError("Error when transforming", new NodeContext(root), Lists.of(err)));
    }
}