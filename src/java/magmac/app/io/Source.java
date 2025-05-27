package magmac.app.io;

import magmac.api.result.Result;

import java.io.IOException;

interface Source {
    String computeName();

    Result<String, IOException> read();

    Location computeLocation();
}
