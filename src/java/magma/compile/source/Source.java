package magma.compile.source;

import magma.collect.list.List_;
import magma.io.IOError;
import magma.result.Result;

public interface Source {
    List_<String> computeNamespace();

    String computeName();

    Result<String, IOError> readString();
}
