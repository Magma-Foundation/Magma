package magmac.app.stage.lexer;

import magmac.api.collect.map.Map;
import magmac.app.compile.node.Node;
import magmac.app.io.Location;
import magmac.app.stage.UnitSet;
import magmac.app.stage.Stage;

public interface Lexer extends Stage<UnitSet<String>, UnitSet<Node>> {
}
