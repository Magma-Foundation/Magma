package magmac.app.io;

import magmac.api.result.Result;

import java.io.IOException;

public interface Source {
    String computeName();

    Result<String, IOException> read();

    Location computeLocation();
}
