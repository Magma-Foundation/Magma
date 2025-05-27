package magmac.app.io.sources;

import magmac.app.io.IOResult;
import magmac.app.io.Location;

import magmac.api.collect.Map;

public interface Sources {
    IOResult<Map<Location, String>> readAll();
}
