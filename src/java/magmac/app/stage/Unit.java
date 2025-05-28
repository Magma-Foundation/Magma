package magmac.app.stage;

import magmac.api.Tuple2;
import magmac.app.io.Location;

public record Unit<T>(Tuple2<Location, T> tuple) {
    public Unit(Location location, T value) {
        this(new Tuple2<>(location, value));
    }

    public Location left() {
        return this.tuple.left();
    }

    public T right() {
        return this.tuple.right();
    }
}
