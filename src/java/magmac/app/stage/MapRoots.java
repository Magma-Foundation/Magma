package magmac.app.stage;

import magmac.api.Tuple2;
import magmac.api.iter.Iter;
import magmac.app.compile.node.Node;
import magmac.app.io.Location;

import magmac.api.collect.Map;

public record MapRoots(Map<Location, Node> roots) implements Roots {
    @Override
    public Iter<Tuple2<Location, Node>> iter() {
        return this.roots.iterEntries();
    }
}
