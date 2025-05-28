package magmac.app.compile.error;

import magmac.api.result.Err;
import magmac.api.result.Ok;
import magmac.api.result.Result;
import magmac.app.compile.error.context.NodeContext;
import magmac.app.compile.error.error.CompileError;
import magmac.app.compile.node.Node;
import magmac.app.error.ImmutableCompileError;

public final class CompileResults {
    @Deprecated
    public static <T> CompileResult<T> fromResult(Result<T, CompileError> result) {
        return new InlineCompileResult<T>(result);
    }

    public static <T> CompileResult<T> fromOk(T value) {
        return new InlineCompileResult<T>(new Ok<T, CompileError>(value));
    }

    public static <R> CompileResult<R> fromErrWithNode(String message, Node context) {
        return new InlineCompileResult<>(new Err<>(new ImmutableCompileError(message, new NodeContext(context))));
    }
}
