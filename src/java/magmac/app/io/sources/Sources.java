package magmac.app.io.sources;

import magmac.api.result.Result;
import magmac.app.io.Location;

import java.io.IOException;
import java.util.Map;

public interface Sources {
    Result<Map<Location, String>, IOException> readAll();
}
