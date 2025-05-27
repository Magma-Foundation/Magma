package magmac.app.compile.error;

import magmac.api.result.Result;

public record CompileResult<T>(Result<T, CompileError> result) {
}
