package magmac.app.stage;

import magmac.api.Tuple2;
import magmac.app.compile.node.Node;
import magmac.app.stage.parse.ParseState;

import java.util.Optional;

public interface Passer {
    Optional<Tuple2<ParseState, Node>> pass(ParseState state, Node node);
}
