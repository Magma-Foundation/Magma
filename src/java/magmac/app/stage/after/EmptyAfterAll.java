package magmac.app.stage.after;

import magmac.app.compile.node.Node;
import magmac.app.stage.unit.UnitSet;

public class EmptyAfterAll implements AfterAll {
    @Override
    public UnitSet<Node> afterAll(UnitSet<Node> roots) {
        return roots;
    }
}
