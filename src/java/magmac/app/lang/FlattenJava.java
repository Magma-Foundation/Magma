package magmac.app.lang;

import magmac.api.None;
import magmac.api.Some;
import magmac.api.Tuple2;
import magmac.api.iter.collect.ListCollector;
import magmac.app.compile.node.InlineNodeList;
import magmac.app.compile.node.Node;
import magmac.app.compile.node.NodeList;
import magmac.app.stage.InlinePassResult;
import magmac.app.stage.Passer;
import magmac.app.stage.parse.ParseState;

public class FlattenJava implements Passer {
    @Override
    public magmac.app.stage.PassResult pass(ParseState state, Node node) {
        if (node.is("root")) {
            NodeList values = new InlineNodeList(node.findNodeList("children")
                    .orElse(InlineNodeList.empty())
                    .iter()
                    .filter(child -> !child.is("package"))
                    .collect(new ListCollector<>()));

            return FlattenJava.getChildren(state, node.withNodeList("children", values));
        }

        if (node.is("record")) {
            return FlattenJava.getChildren(state, node.retype("class"));
        }

        return InlinePassResult.empty();
    }

    private static InlinePassResult getChildren(ParseState state, Node node) {
        return new InlinePassResult(new Some<>(new Tuple2<ParseState, Node>(state, node)));
    }
}