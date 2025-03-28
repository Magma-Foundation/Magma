package magma.app.compile;

import magma.api.collect.List_;
import magma.api.result.Result;

import java.io.IOException;

public interface Source {
    Result<String, IOException> read();

    List_<String> computeNamespace();

    String computeName();
}
