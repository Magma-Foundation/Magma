package magmac.app.stage.after;

import magmac.app.compile.node.Node;
import magmac.app.stage.unit.UnitSet;

public interface AfterAll {
    UnitSet<Node> afterAll(UnitSet<Node> roots);
}
