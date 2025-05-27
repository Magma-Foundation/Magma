package magmac.app.stage;

import magmac.api.Tuple2;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.stage.parse.ParseState;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class BeforePasser implements Passer {
    @Override
    public Optional<Tuple2<ParseState, Node>> pass(ParseState state, Node node) {
        if (!node.is("import")) {
            return Optional.empty();
        }

        String child = node.findNodeList("segments")
                .orElse(Collections.emptyList())
                .getLast()
                .findString("value")
                .orElse("");

        Node dependency = new MapNode("dependency")
                .withString("parent", state.location().name())
                .withString("child", child);

        return Optional.of(new Tuple2<ParseState, Node>(state, dependency));
    }
}