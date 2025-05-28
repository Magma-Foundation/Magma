package magmac.app.stage;

import magmac.api.iter.Iter;

public interface UnitSet<T> {
    Iter<Unit<T>> iter();
}
