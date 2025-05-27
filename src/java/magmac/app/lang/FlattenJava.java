package magmac.app.lang;

import magmac.api.Tuple2;
import magmac.api.collect.ListCollector;
import magmac.app.compile.node.InlineNodeList;
import magmac.app.compile.node.Node;
import magmac.app.stage.Passer;
import magmac.app.stage.parse.ParseState;

import java.util.List;
import java.util.Optional;

public class FlattenJava implements Passer {
    @Override
    public Optional<Tuple2<ParseState, Node>> pass(ParseState state, Node node) {
        if (node.is("root")) {
            List<Node> newChildren = node.findNodeList("children")
                    .orElse(InlineNodeList.empty())
                    .iter()
                    .filter(child -> !child.is("package"))
                    .collect(new ListCollector<>());

            return Optional.of(new Tuple2<>(state, node.withNodeList("children", new InlineNodeList(newChildren))));
        }

        if (node.is("record")) {
            return Optional.of(new Tuple2<>(state, node.retype("class")));
        }

        return Optional.empty();
    }
}