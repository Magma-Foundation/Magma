package magmac.app.stage.generate;

import magmac.app.compile.node.Node;
import magmac.app.io.Location;
import magmac.app.stage.UnitSet;
import magmac.app.stage.Stage;

import magmac.api.collect.map.Map;

public interface Generator extends Stage<UnitSet<Node>, Map<Location, String>> {
}
