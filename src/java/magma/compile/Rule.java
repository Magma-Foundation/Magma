package magma.compile;

import magma.result.Result;

public interface Rule {
    Result<String, CompileError> compile(String input);
}
