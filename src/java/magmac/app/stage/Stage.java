package magmac.app.stage;

import magmac.api.result.Result;
import magmac.app.compile.error.CompileError;

public interface Stage<T, R> {
    Result<R, CompileError> apply(T initial);
}
