package magmac.app.compile.error;

import magmac.api.result.Ok;
import magmac.api.result.Result;
import magmac.app.compile.error.error.CompileError;

public final class CompileResults {
    public static <T> CompileResult<T> fromResult(Result<T, CompileError> result) {
        return new InlineCompileResult<T>(result);
    }

    public static <T> CompileResult<T> fromOk(T value) {
        return CompileResults.fromResult(new Ok<>(value));
    }
}
