package magmac.app.io.targets;

import java.io.IOException;

import magmac.api.Option;
import magmac.app.stage.unit.UnitSet;

/**
 * Destination for compiler output such as files or network locations.
 */
public interface Targets {
    Option<IOException> writeAll(UnitSet<String> outputs);
}
