package magma.compile.lang;

import magma.api.Tuple;
import magma.api.result.Ok;
import magma.api.result.Result;
import magma.api.stream.Streams;
import magma.compile.CompileError;
import magma.compile.attribute.Attribute;
import magma.compile.attribute.Attributes;
import magma.compile.attribute.MapAttributes;
import magma.compile.attribute.NodeAttribute;
import magma.compile.attribute.NodeListAttribute;
import magma.compile.rule.Node;

import java.util.ArrayList;
import java.util.List;

public class Generator {
    private Result<Tuple<Attribute, State>, CompileError> generateAttribute(Attribute attribute, State state) {
        var nodeList = attribute.asNodeList();
        if (nodeList.isPresent()) {
            var initial = new Tuple<List<Node>, State>(new ArrayList<>(), state);
            return Streams.fromNativeList(nodeList.get())
                    .foldRightToResult(initial, this::generateThenFold)
                    .mapValue(tuple -> tuple.mapLeft(NodeListAttribute::new));
        }

        return attribute.asNode()
                .map(value -> generateWithState(value, state).mapValue(inner -> inner.<Attribute>mapLeft(NodeAttribute::new)))
                .orElseGet(() -> new Ok<>(new Tuple<>(attribute, state)));

    }

    private Result<Tuple<List<Node>, State>, CompileError> generateThenFold(Tuple<List<Node>, State> current, Node node) {
        return generateWithState(node, current.right()).mapValue(tuple -> {
            var newNode = tuple.left();
            var newState = tuple.right();

            var list = current.left();
            list.add(newNode);

            return new Tuple<>(list, newState);
        });
    }

    public Result<Node, CompileError> generate(Node node, State state) {
        return generateWithState(node, state).mapValue(Tuple::left);
    }

    private Result<Tuple<Node, State>, CompileError> generateWithState(Node node, State depth) {
        var preVisitedTuple = preVisit(node, depth);

        var preVisited = preVisitedTuple.left();
        var preVisitedAttributes = preVisited.attributes().streamEntries().toList();
        var preVisitedState = preVisitedTuple.right();

        return Streams.fromNativeList(preVisitedAttributes)
                .foldRightToResult(new Tuple<>(new MapAttributes(), preVisitedState), this::generateAttributeWithState)
                .mapValue(tuple -> postVisit(preVisited.withAttributes(tuple.left()), tuple.right()));
    }

    private Result<Tuple<Attributes, State>, CompileError> generateAttributeWithState(
            Tuple<Attributes, State> current,
            Tuple<String, Attribute> next) {

        var key = next.left();
        var value = next.right();

        return generateAttribute(value, current.right()).mapValue(inner -> new Tuple<>(current.left().with(key, inner.left()), inner.right()));
    }

    protected Tuple<Node, State> preVisit(Node node, State depth) {
        return new Tuple<>(node, depth);
    }

    protected Tuple<Node, State> postVisit(Node node, State depth) {
        return new Tuple<>(node, depth);
    }
}
