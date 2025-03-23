package magma;

import magma.error.CompileError;
import magma.result.Result;

public interface Compiler {
    Result<String, CompileError> compile(String input);
}
