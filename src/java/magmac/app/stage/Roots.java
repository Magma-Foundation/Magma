package magmac.app.stage;

import magmac.api.Tuple2;
import magmac.api.iter.Iter;
import magmac.app.compile.node.Node;
import magmac.app.io.Location;

public interface Roots {
    Iter<Tuple2<Location, Node>> iter();
}
