package magmac.app.stage;

import magmac.app.compile.node.Node;
import magmac.app.io.Location;

import java.util.Map;

public class EmptyAfterAll implements AfterAll {
    @Override
    public Map<Location, Node> afterAll(Map<Location, Node> roots) {
        return roots;
    }
}
