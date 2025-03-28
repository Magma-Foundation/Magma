package magma.compile;

import magma.result.Result;

import java.io.IOException;
import java.util.List;

public interface Source {
    Result<String, IOException> read();

    List<String> computeNamespace();

    String computeName();
}
