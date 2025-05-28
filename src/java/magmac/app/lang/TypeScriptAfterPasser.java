package magmac.app.lang;

import magmac.api.None;
import magmac.api.Option;
import magmac.api.Some;
import magmac.api.collect.list.Lists;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.error.CompileResults;
import magmac.app.compile.node.InlineNodeList;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.compile.node.NodeList;
import magmac.app.compile.node.NodeListCollector;
import magmac.app.stage.Passer;
import magmac.app.stage.parse.ParseState;
import magmac.app.stage.result.InlinePassResult;
import magmac.app.stage.result.ParseResult;
import magmac.app.stage.unit.ParseUnit;
import magmac.app.stage.unit.ParseUnitImpl;

public class TypeScriptAfterPasser implements Passer {
    private static Option<ParseResult> passImport(ParseState state, Node node) {
        if (!node.is("import")) {
            return new None<>();
        }

        int namespaceSize = state.findLocation().namespace().size();
        NodeList copy = Lists.repeat("..", namespaceSize)
                .iter()
                .map((String value) -> new MapNode().withString("value", value))
                .collect(new NodeListCollector());
        NodeList segments = node.findNodeList("segments")
                .orElse(InlineNodeList.empty());

        Option<CompileResult<ParseUnit<Node>>> map = segments.findLast().map((Node last) -> {
            String value = last.findString("value").orElse("");
            NodeList values = copy.addAll(segments);
            Node node1 = node.withString("child", value).withNodeList("segments", values);
            return CompileResults.Ok(new ParseUnitImpl<Node>(state, node1));
        });

        return new Some<>(new InlinePassResult(map));
    }

    private static Option<ParseResult> passMethod(ParseState state, Node node) {
        if (node.is("method")) {
            Node header = node.findNode("header").orElse(new MapNode());
            NodeList parameters = node.findNodeList("parameters").orElse(InlineNodeList.empty());
            Node withParameters = header.withNodeList("parameters", parameters);
            return new Some<>(InlinePassResult.from(state, node.withNode("header", withParameters)
                    .withString("after-children", "\n\t")));
        }

        return new None<>();
    }

    private static Option<ParseResult> passVariadic(ParseState state, Node node) {
        if (node.is("variadic")) {
            return new Some<>(InlinePassResult.from(state, node.retype("array")));
        }
        else {
            return new None<>();
        }
    }

    private static Option<ParseResult> format(ParseState state, Node node) {
        if (node.is("statement") || node.is("block")) {
            Node before = node.withString("before", "\n\t\t");
            return new Some<>(InlinePassResult.from(state, before));
        }

        return new None<>();
    }

    @Override
    public ParseResult pass(ParseState state, Node node) {
        return TypeScriptAfterPasser.passImport(state, node)
                .or(() -> TypeScriptAfterPasser.passMethod(state, node))
                .or(() -> TypeScriptAfterPasser.format(state, node))
                .or(() -> TypeScriptAfterPasser.passVariadic(state, node))
                .or(() -> this.passTemp(state, node))
                .orElseGet(() -> InlinePassResult.empty());
    }

    private Option<ParseResult> passTemp(ParseState state, Node node) {
        if (node.is("statement")) {
            var value1 = node.findNodeOrError("value")
                    .flatMapValue((Node value) -> this.getObjectCompileResult(state, value));

            return new Some<>(new InlinePassResult(new Some<>(value1)));
        }

        return new None<>();
    }

    private CompileResult<ParseUnit<Node>> getObjectCompileResult(ParseState state, Node value) {
        if (value.is("assignment")) {
            return value.findNodeOrError("destination")
                    .mapValue((Node destination) -> this.getNode(destination))
                    .mapValue((Node result) -> new ParseUnitImpl<>(state, result));
        }

        return CompileResults.Ok(new ParseUnitImpl<>(state, value));
    }

    private Node getNode(Node destination) {
        if (destination.is("definition")) {
            NodeList modifiers = destination.findNodeList("modifiers").orElse(InlineNodeList.empty());
            NodeList newModifiers = modifiers.add(new MapNode().withString("value", "let"));
            return destination.withNodeList("modifiers", newModifiers);
        }
        else {
            return destination;
        }
    }
}
