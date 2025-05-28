package magmac.app.stage;

import magmac.api.collect.map.Map;
import magmac.api.collect.map.Maps;
import magmac.api.iter.Iter;
import magmac.app.io.Location;

public record MapUnitSet<T>(Map<Location, T> roots) implements UnitSet<T> {
    public MapUnitSet() {
        this(Maps.empty());
    }

    @Override
    public Iter<Unit<T>> iter() {
        return this.roots.iterEntries().map(tuple -> new Unit<>(tuple));
    }

    @Override
    public UnitSet<T> add(Unit<T> element) {
        return new MapUnitSet<>(this.roots.put(element.left(), element.right()));
    }
}
