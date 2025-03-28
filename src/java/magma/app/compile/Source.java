package magma.app.compile;

import magma.api.collect.List_;
import magma.api.io.IOError;
import magma.api.result.Result;

public interface Source {
    Result<String, IOError> read();

    List_<String> computeNamespace();

    String computeName();
}
