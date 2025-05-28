package magmac.app.stage.unit;

import magmac.api.iter.Iter;

public interface UnitSet<T> {
    Iter<T> iterValues();

    Iter<Unit<T>> iter();

    UnitSet<T> add(Unit<T> element);
}
