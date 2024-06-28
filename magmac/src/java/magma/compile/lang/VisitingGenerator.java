package magma.compile.lang;

import magma.api.Tuple;
import magma.api.collect.stream.Streams;
import magma.api.result.Ok;
import magma.api.result.Result;
import magma.compile.CompileParentError;
import magma.compile.Error_;
import magma.compile.annotate.State;
import magma.compile.attribute.Attribute;
import magma.compile.attribute.Attributes;
import magma.compile.attribute.MapAttributes;
import magma.compile.attribute.NodeAttribute;
import magma.compile.attribute.NodeListAttribute;
import magma.compile.rule.Node;

import java.util.ArrayList;
import java.util.List;

public class VisitingGenerator implements Generator {
    private final Visitor visitor;

    public VisitingGenerator(Visitor visitor) {
        this.visitor = visitor;
    }

    private Result<Tuple<Attribute, State>, Error_> generateAttribute(Attribute attribute, State state) {
        var nodeList = attribute.asNodeList();
        if (nodeList.isPresent()) {
            var initial = new Tuple<List<Node>, State>(new ArrayList<>(), state);
            return Streams.fromNativeList(nodeList.get())
                    .foldLeftToResult(initial, this::generateThenFold)
                    .mapValue(tuple -> tuple.mapLeft(NodeListAttribute::new));
        }

        return attribute.asNode()
                .map(value -> generate(value, state).mapValue(inner -> inner.mapLeft(NodeAttribute::from)))
                .orElseGet(() -> new Ok<>(new Tuple<>(attribute, state)));

    }

    private Result<Tuple<List<Node>, State>, Error_> generateThenFold(Tuple<List<Node>, State> current, Node node) {
        return generate(node, current.right()).mapValue(tuple -> {
            var newNode = tuple.left();
            var newState = tuple.right();

            var list = current.left();
            list.add(newNode);

            return new Tuple<>(list, newState);
        });
    }

    @Override
    public Result<Tuple<Node, State>, Error_> generate(Node node, State depth) {
        return visitor.preVisit(node, depth).flatMapValue(preVisitedTuple -> {
            var preVisited = preVisitedTuple.left();
            var preVisitedAttributes = preVisited.attributes().streamEntries().toList();
            var preVisitedState = preVisitedTuple.right();

            return Streams.fromNativeList(preVisitedAttributes)
                    .foldLeftToResult(new Tuple<>(new MapAttributes(), preVisitedState), this::generateAttributeWithState)
                    .flatMapValue(tuple -> visitor.postVisit(preVisited.withAttributes(tuple.left()), tuple.right()));
        }).mapErr(err -> new CompileParentError("Failed to parse node.", node.toString(), err));
    }

    private Result<Tuple<Attributes, State>, Error_> generateAttributeWithState(
            Tuple<Attributes, State> current,
            Tuple<String, Attribute> next) {

        var key = next.left();
        var value = next.right();

        return generateAttribute(value, current.right()).mapValue(inner -> new Tuple<>(current.left().with(key, inner.left()), inner.right()));
    }

}