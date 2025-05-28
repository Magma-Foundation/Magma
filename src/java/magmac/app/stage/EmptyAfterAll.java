package magmac.app.stage;

import magmac.app.compile.node.Node;

public class EmptyAfterAll implements AfterAll {
    @Override
    public UnitSet<Node> afterAll(UnitSet<Node> roots) {
        return roots;
    }
}
