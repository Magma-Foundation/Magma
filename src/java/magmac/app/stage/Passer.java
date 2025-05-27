package magmac.app.stage;

import magmac.api.Tuple2;
import magmac.app.compile.node.Node;
import magmac.app.stage.parse.ParseState;

import magmac.api.Option;

public interface Passer {
    Option<Tuple2<ParseState, Node>> pass(ParseState state, Node node);
}
