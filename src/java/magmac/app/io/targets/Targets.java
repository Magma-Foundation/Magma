package magmac.app.io.targets;

import magmac.app.io.Location;

import java.io.IOException;
import magmac.api.collect.map.Map;
import magmac.api.Option;

public interface Targets {
    Option<IOException> writeAll(Map<Location, String> outputs);
}
