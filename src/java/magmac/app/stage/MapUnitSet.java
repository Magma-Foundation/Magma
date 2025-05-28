package magmac.app.stage;

import magmac.api.Tuple2;
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
        return this.roots.iter().map((Tuple2<Location, T> entry) -> new SimpleUnit<>(entry.left(), entry.right()));
    }

    @Override
    public UnitSet<T> add(Unit<T> element) {
        return new MapUnitSet<>(element.deconstruct((Location key, T value) -> this.roots.put(key, value)));
    }

    @Override
    public Iter<T> iterValues() {
        return this.roots.iter().map((Tuple2<Location, T> entry) -> entry.right());
    }
}
