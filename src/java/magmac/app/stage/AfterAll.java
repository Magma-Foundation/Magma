package magmac.app.stage;

import magmac.app.compile.node.Node;

public interface AfterAll {
    UnitSet<Node> afterAll(UnitSet<Node> roots);
}
