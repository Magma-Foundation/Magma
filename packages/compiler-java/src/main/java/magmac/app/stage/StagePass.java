package magmac.app.stage;

import magmac.app.compile.node.Node;
import magmac.app.stage.parse.ParseState;
import magmac.app.stage.result.ParseResult;

/**
 * Performs a single pass over a parse tree.
 */
public interface StagePass {
    /**
     * Processes the given {@code node} within the provided parse state.
     */
    ParseResult pass(ParseState state, Node node);
}
