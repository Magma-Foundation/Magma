package magmac.app.stage;

import magmac.app.compile.node.Node;
import magmac.app.io.Location;

import java.util.Map;

public interface Lexer {
    Map<Location, Node> lexAll(Map<Location, String> values);
}
