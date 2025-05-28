package magmac.app.stage;

import magmac.api.Tuple2;
import magmac.app.io.Location;

public record Unit<T>(Tuple2<Location, T> tuple) {
}
