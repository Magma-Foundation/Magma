package magmac.app.lang;

import magmac.api.Some;
import magmac.api.iter.collect.ListCollector;
import magmac.app.compile.error.CompileResults;
import magmac.app.compile.node.InlineNodeList;
import magmac.app.compile.node.Node;
import magmac.app.compile.node.NodeList;
import magmac.app.stage.result.InlinePassResult;
import magmac.app.stage.result.ParseResult;
import magmac.app.stage.unit.ParseUnit;
import magmac.app.stage.unit.ParseUnitImpl;
import magmac.app.stage.Passer;
import magmac.app.stage.parse.ParseState;

public class FlattenJava implements Passer {
    private static InlinePassResult getChildren(ParseState state, Node node) {
        ParseUnit<Node> parseStateNodeTuple2 = new ParseUnitImpl<Node>(state, node);
        return new InlinePassResult(new Some<>(CompileResults.fromOk(parseStateNodeTuple2)));
    }

    @Override
    public ParseResult pass(ParseState state, Node node) {
        if (node.is("root")) {
            NodeList values = new InlineNodeList(node.findNodeList("children")
                    .orElse(InlineNodeList.empty())
                    .iter()
                    .filter((Node child) -> !child.is("package"))
                    .collect(new ListCollector<>()));

            return FlattenJava.getChildren(state, node.withNodeList("children", values));
        }

        if (node.is("record")) {
            return FlattenJava.getChildren(state, node.retype("class"));
        }

        return InlinePassResult.empty();
    }
}