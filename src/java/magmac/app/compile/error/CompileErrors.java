package magmac.app.compile.error;

import magmac.api.result.Err;
import magmac.api.result.Result;
import magmac.app.compile.error.context.NodeContext;
import magmac.app.compile.error.context.StringContext;
import magmac.app.compile.node.Node;
import magmac.app.error.ImmutableCompileError;

public final class CompileErrors {
    public static <T> Result<T, CompileError> createNodeError(String message, Node node) {
        return new Err<>(new ImmutableCompileError(message, new NodeContext(node)));
    }

    public static <T> Result<T, CompileError> createStringError(String message, String context) {
        return new Err<>(new ImmutableCompileError(message, new StringContext(context)));
    }
}
