package magma.app.io;

import magma.api.io.IOError;
import magma.api.result.Result;
import magma.app.Location;

public interface Source {
    Result<String, IOError> read();

    Location createLocation();
}
