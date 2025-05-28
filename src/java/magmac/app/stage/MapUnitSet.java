package magmac.app.stage;

import magmac.api.Tuple2;
import magmac.api.iter.Iter;
import magmac.app.compile.node.Node;
import magmac.app.io.Location;

import magmac.api.collect.map.Map;

public record MapUnitSet(Map<Location, Node> roots) implements UnitSet<Node> {
    private Iter<Tuple2<Location, Node>> iter0() {
        return this.roots.iterEntries();
    }

    @Override
    public Iter<Unit<Node>> iter() {
        return this.iter0().map(tuple -> new Unit<>(tuple));
    }
}
