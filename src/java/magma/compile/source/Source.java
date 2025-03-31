package magma.compile.source;

import magma.io.IOError;
import magma.result.Result;

public interface Source {
    Location location();

    Result<String, IOError> readString();
}
