package magmac.app.stage;

import magmac.app.compile.node.Node;
import magmac.app.io.Location;

import java.util.Map;

public interface Parser {
    Map<Location, Node> parseAll(Map<Location, Node> roots);
}
