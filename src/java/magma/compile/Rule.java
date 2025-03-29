package magma.compile;

import magma.result.Result;

public interface Rule {
    Result<String, CompileException> compile(String input);
}
