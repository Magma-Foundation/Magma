package magmac.app.io.sources;

import magmac.api.result.Result;
import magmac.app.io.Location;

import java.io.IOException;

interface Source {
    String computeName();

    Result<String, IOException> read();

    Location computeLocation();
}
