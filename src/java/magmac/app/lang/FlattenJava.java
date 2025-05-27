package magmac.app.lang;

import magmac.api.None;
import magmac.api.Some;
import magmac.api.Tuple2;
import magmac.api.collect.ListCollector;
import magmac.app.compile.node.InlineNodeList;
import magmac.app.compile.node.Node;
import magmac.app.compile.node.NodeList;
import magmac.app.stage.Passer;
import magmac.app.stage.parse.ParseState;

import magmac.api.Option;

public class FlattenJava implements Passer {
    @Override
    public Option<Tuple2<ParseState, Node>> pass(ParseState state, Node node) {
        if (node.is("root")) {
            NodeList values = new InlineNodeList(node.findNodeList("children")
                    .orElse(InlineNodeList.empty())
                    .iter()
                    .filter(child -> !child.is("package"))
                    .collect(new ListCollector<>()));

            return new Some<>(new Tuple2<ParseState, Node>(state, node.withNodeList("children", values)));
        }

        if (node.is("record")) {
            return new Some<>(new Tuple2<ParseState, Node>(state, node.retype("class")));
        }

        return new None<>();
    }
}