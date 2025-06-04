package magmac.app.stage.lexer;

import magmac.app.compile.node.Node;
import magmac.app.stage.unit.UnitSet;
import magmac.app.stage.Stage;

/**
 * Stage that converts raw text into a tree of {@link Node}s.
 */
public interface Lexer extends Stage<UnitSet<String>, UnitSet<Node>> {
}
