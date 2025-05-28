package magmac.app.io.targets;

import java.io.IOException;

import magmac.api.Option;
import magmac.app.stage.UnitSet;

public interface Targets {
    Option<IOException> writeAll(UnitSet<String> outputs);
}
