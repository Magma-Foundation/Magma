package magmac.app.stage.generate;

import magmac.app.compile.node.Node;
import magmac.app.stage.unit.UnitSet;
import magmac.app.stage.Stage;

/**
 * Stage producing textual output from AST nodes.
 */
public interface Generator extends Stage<UnitSet<Node>, UnitSet<String>> {
}
