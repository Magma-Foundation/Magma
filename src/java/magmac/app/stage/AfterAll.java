package magmac.app.stage;

import magmac.app.compile.node.Node;
import magmac.app.io.Location;

import java.util.Map;

public interface AfterAll {
    Map<Location, Node> afterAll(Map<Location, Node> roots);
}
