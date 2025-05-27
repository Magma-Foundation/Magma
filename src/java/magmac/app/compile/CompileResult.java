package magmac.app.compile;

import magmac.api.result.Result;
import magmac.app.compile.error.CompileError;

public record CompileResult<T>(Result<T, CompileError> result) {
}
