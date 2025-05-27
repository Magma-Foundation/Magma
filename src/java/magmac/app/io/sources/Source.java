package magmac.app.io.sources;

import magmac.app.io.IOResult;
import magmac.app.io.Location;

interface Source {
    String computeName();

    IOResult<String> read();

    Location computeLocation();
}
