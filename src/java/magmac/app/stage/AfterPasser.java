package magmac.app.stage;

import magmac.api.Tuple2;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.stage.parse.ParseState;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AfterPasser implements Passer {
    @Override
    public Optional<Tuple2<ParseState, Node>> pass(ParseState state, Node node) {
        if (node.is("root")) {
            List<Node> copy = new ArrayList<Node>();
            copy.add(new MapNode("class").withString("name", state.location().name()));
            copy.addAll(node.findNodeList("children").orElse(new ArrayList<Node>()));
            return Optional.of(new Tuple2<ParseState, Node>(state, node.withNodeList("children", copy)));
        }

        return Optional.empty();
    }
}