package magmac.app.stage;

import magmac.app.compile.node.Node;
import magmac.app.stage.parse.ParseState;
import magmac.app.stage.result.ParseResult;

public interface Passer {
    ParseResult pass(ParseState state, Node node);
}
