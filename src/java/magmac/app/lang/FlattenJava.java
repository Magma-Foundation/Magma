package magmac.app.lang;

import magmac.api.Some;
import magmac.api.Tuple2;
import magmac.api.iter.collect.ListCollector;
import magmac.api.result.Ok;
import magmac.app.compile.error.InlineCompileResult;
import magmac.app.compile.node.InlineNodeList;
import magmac.app.compile.node.Node;
import magmac.app.compile.node.NodeList;
import magmac.app.stage.InlinePassResult;
import magmac.app.stage.PassResult;
import magmac.app.stage.Passer;
import magmac.app.stage.parse.ParseState;

public class FlattenJava implements Passer {
    private static InlinePassResult getChildren(ParseState state, Node node) {
        Tuple2<ParseState, Node> parseStateNodeTuple2 = new Tuple2<>(state, node);
        return new InlinePassResult(new Some<>(InlineCompileResult.fromOk(parseStateNodeTuple2)));
    }

    @Override
    public PassResult pass(ParseState state, Node node) {
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