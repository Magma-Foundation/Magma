package magmac.app.stage;

import magmac.app.compile.node.Node;
import magmac.app.stage.parse.ParseState;

public interface Passer {
    ParseResult pass(ParseState state, Node node);
}
