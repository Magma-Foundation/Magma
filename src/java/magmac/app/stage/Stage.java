package magmac.app.stage;

import magmac.app.compile.error.CompileResult;

public interface Stage<T, R> {
    CompileResult<R> apply(T initial);
}
