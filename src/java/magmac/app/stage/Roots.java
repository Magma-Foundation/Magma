package magmac.app.stage;

import magmac.app.compile.node.Node;
import magmac.app.io.Location;

import java.util.Map;

public record Roots(Map<Location, Node> roots) {
}
