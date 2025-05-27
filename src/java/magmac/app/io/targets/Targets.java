package magmac.app.io.targets;

import magmac.app.io.Location;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

public interface Targets {
    Optional<IOException> writeAll(Map<Location, String> outputs);
}
