package magma.compile.rule;

import magma.compile.CompileError;
import magma.result.Result;

public interface Rule {
    Result<String, CompileError> compile(String input);
}
