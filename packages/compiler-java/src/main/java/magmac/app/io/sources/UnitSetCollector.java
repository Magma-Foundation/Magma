package magmac.app.io.sources;

import magmac.api.iter.collect.Collector;
import magmac.app.stage.unit.MapUnitSet;
import magmac.app.stage.unit.Unit;
import magmac.app.stage.unit.UnitSet;

public class UnitSetCollector<T> implements Collector<Unit<T>, UnitSet<T>> {
    @Override
    public UnitSet<T> createInitial() {
        return new MapUnitSet<>();
    }

    @Override
    public UnitSet<T> fold(UnitSet<T> current, Unit<T> element) {
        return current.add(element);
    }
}
