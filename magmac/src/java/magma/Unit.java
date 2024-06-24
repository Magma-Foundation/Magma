package magma;

import magma.api.result.Result;
import magma.compile.CompileException;

import java.util.List;

public interface Unit {
    Result<String, CompileException> read();

    List<String> computeNamespace();

    String computeName();
}
